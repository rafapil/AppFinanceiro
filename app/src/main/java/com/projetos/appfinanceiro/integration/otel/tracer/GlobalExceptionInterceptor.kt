package com.projetos.appfinanceiro.integration.otel.tracer

import android.app.Application
import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.api.trace.Tracer

object GlobalExceptionInterceptor {

    fun initialize(application: Application) {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val tracer: Tracer = TracerOpenTelemetryUtil.getTracer()!!
            val span = tracer.spanBuilder("Uncaught Exception").startSpan()
            try {
                span.makeCurrent().use {
                    span.setStatus(StatusCode.ERROR, "Uncaught Exception")
                    span.setAttribute("thread", thread.name)
                    span.recordException(throwable)
                }
            } finally {
                span.end()
            }

            // Propaga para o handler padrão derrubou o app algumas vezes!!!!
            Thread.getDefaultUncaughtExceptionHandler()?.uncaughtException(thread, throwable)
        }
    }
}
