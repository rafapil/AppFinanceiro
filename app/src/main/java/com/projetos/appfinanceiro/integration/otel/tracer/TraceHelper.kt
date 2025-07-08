package com.projetos.appfinanceiro.integration.otel.tracer

//package com.projetos.appfinanceiro.tracing

import android.app.Activity
import android.content.Intent
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanContext
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.api.trace.TraceFlags
import io.opentelemetry.api.trace.TraceState
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.StatusCode




object TraceHelper {

    private val tracer: Tracer by lazy { TracerOpenTelemetryUtil.getTracer()!! }

    /**
     * Executa uma função com span automático
     */
    fun <T> withSpan(
        name: String,
        attributes: Map<String, Any>? = null,
        block: (Span) -> T
    ): T {
        val span = tracer.spanBuilder(name).startSpan()
        try {
            span.makeCurrent().use {
                attributes?.forEach { (key, value) ->
                    when (value) {
                        is String -> span.setAttribute(key, value)
                        is Long -> span.setAttribute(key, value)
                        is Double -> span.setAttribute(key, value)
                        is Boolean -> span.setAttribute(key, value)
                        else -> span.setAttribute(key, value.toString())
                    }
                }
                return block(span)
            }
        } catch (e: Exception) {
            span.setStatus(StatusCode.ERROR, "Exception during span: ${e.message}")
            span.recordException(e)
            throw e
        } finally {
            span.end()
        }
    }

    /**
     * Cria um evento dentro de um span já existente
     */
    fun addEventToCurrentSpan(name: String, attributes: Map<String, Any>? = null) {
        val currentSpan = Span.current()
        if (currentSpan != null && currentSpan.spanContext.isValid) {
            val eventAttributes = attributes?.let {
                val builder = Attributes.builder()
                for ((key, value) in it) {
                    when (value) {
                        is String -> builder.put(AttributeKey.stringKey(key), value)
                        is Long -> builder.put(AttributeKey.longKey(key), value)
                        is Double -> builder.put(AttributeKey.doubleKey(key), value)
                        is Boolean -> builder.put(AttributeKey.booleanKey(key), value)
                        else -> builder.put(AttributeKey.stringKey(key), value.toString())
                    }
                }
                builder.build()
            }
            currentSpan.addEvent(name, eventAttributes ?: Attributes.empty())
        }
    }

    /**
     * Cria um span manualmente se precisar de controle mais fino
     */
    fun startSpan(name: String): Span {
        return tracer.spanBuilder(name).startSpan()
    }

    /**
     * Cria um span e direciona para uma nova activity
     */
    fun navigateWithSpan(
        activity: Activity,
        destination: Class<*>,
        spanName: String
    ) {
        val tracer = TracerOpenTelemetryUtil.getTracer()!!
        val span = tracer.spanBuilder(spanName).startSpan()
        try {
            span.makeCurrent().use {
                val intent = Intent(activity, destination).apply {
                    putExtra("traceId", span.spanContext.traceId)
                    putExtra("spanId", span.spanContext.spanId)
                }
                activity.startActivity(intent)
            }
        } finally {
            span.end()
        }
    }

    /**
     * Cria um ChildSpan a partir da intent
     */
    fun createChildSpanFromIntent(
        intent: Intent,
        spanName: String,
        additionalAttributes: Map<String, Any>? = null
    ): Span? {
        val traceId = intent.getStringExtra("traceId")
        val spanId = intent.getStringExtra("spanId")

        return if (!traceId.isNullOrBlank() && !spanId.isNullOrBlank()) {
            val tracer: Tracer = TracerOpenTelemetryUtil.getTracer()!!
            val parentSpanContext = SpanContext.createFromRemoteParent(
                traceId,
                spanId,
                TraceFlags.getSampled(),
                TraceState.getDefault()
            )
            val parentContext = Context.root().with(Span.wrap(parentSpanContext))

            val span = tracer.spanBuilder(spanName)
                .setParent(parentContext)
                .setSpanKind(SpanKind.INTERNAL)
                .startSpan()

            additionalAttributes?.forEach { (key, value) ->
                when (value) {
                    is String -> span.setAttribute(key, value)
                    is Long -> span.setAttribute(key, value)
                    is Double -> span.setAttribute(key, value)
                    is Boolean -> span.setAttribute(key, value)
                    else -> span.setAttribute(key, value.toString())
                }
            }

            span
        } else null
    }


}
