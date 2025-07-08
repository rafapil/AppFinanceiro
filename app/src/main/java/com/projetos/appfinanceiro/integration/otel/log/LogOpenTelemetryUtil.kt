package com.projetos.appfinanceiro.integration.otel.log

import com.projetos.appfinanceiro.integration.config.NetworkingConstants
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes


import android.app.Application
//import io.opentelemetry.api.OpenTelemetry
//import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.logs.Logger
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.api.logs.Severity
//import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.exporter.otlp.http.logs.OtlpHttpLogRecordExporter
//import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.logs.export.SimpleLogRecordProcessor
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter
import io.opentelemetry.sdk.logs.LogRecordProcessor
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor.*

//import io.opentelemetry.sdk.resources.Resource
//import io.opentelemetry.semconv.resource.attributes.ResourceAttributes

import android.util.Log.*


/*
* Nao foi obtido resultado satisfatorio, por mais que o codigo esteja funcionando, ele não loga
* foi testado enviando para o dynatrace e para o coletor o OTEL sem resultados satisfatorios
* */

object LogOpenTelemetryUtil {

    private var logger: Logger? = null
    private lateinit var sdkLoggerProvider: SdkLoggerProvider

    @JvmStatic
    fun init() {
        val resource = Resource.getDefault().merge(
            Resource.create(
                Attributes.of(
                    ResourceAttributes.SERVICE_NAME, "AppFinanceiro"
                )
            )
        )

//        val exporter = OtlpHttpLogRecordExporter.builder()
//            .setEndpoint("https://{{sua_env}}.live.dynatrace.com/api/v2/otlp/v1/logs") // substitua com seu ID
//            .addHeader(
//                "Authorization",
//                "Api-Token {{seu_token}}}"
//            ) // token Dynatrace
//            .build()
//
//        val logProcessor = BatchLogRecordProcessor.builder(exporter).build()
//
//        sdkLoggerProvider = SdkLoggerProvider.builder()
//            .addLogRecordProcessor(logProcessor)
//            .setResource(resource)
//            .build()

        val openTelemetry = OpenTelemetrySdk.builder()
//            .setLoggerProvider(sdkLoggerProvider)
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .build()

        logger = openTelemetry.getLogsBridge().get("AppLogger")
    }

    @JvmStatic
    fun getLogger(): Logger? = logger

    @JvmStatic
    fun flush() {
        sdkLoggerProvider.forceFlush().join(2, java.util.concurrent.TimeUnit.SECONDS)
    }
}
