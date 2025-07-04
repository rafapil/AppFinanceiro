package com.projetos.appfinanceiro.integration.otel

import com.projetos.appfinanceiro.integration.config.NetworkingConstants
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.logging.LoggingSpanExporter
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes

class OpenTelemetryUtil {
    companion object {
        @JvmStatic
        fun init() {
            val otelResource = Resource.getDefault().merge(
                Resource.create(
                    Attributes.of(
                        ResourceAttributes.SERVICE_NAME, "AppFinanceiro",
                        ResourceAttributes.HOST_NAME, "app-financeiro-gateway"
                    )
                )
            )

//             gRPC
//            val sdkTracerProvider = SdkTracerProvider.builder()
//                .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
//                .addSpanProcessor(
//                    BatchSpanProcessor.builder(
//                        OtlpGrpcSpanExporter.builder()
//                            .setEndpoint("https://{{URL_TOOL}}.live.dynatrace.com/api/v2/otlp")
//                            .addHeader("Authorization", "Api-Token apiKey")
//                            .build()
//                    ).build()
//                )
//                .setResource(otelResource)
//                .build()


            // http
            val sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                .addSpanProcessor(BatchSpanProcessor.builder(
                    OtlpHttpSpanExporter.builder()
                        .setEndpoint(NetworkingConstants.BASE_URL_TRACE)
                        .addHeader("Authorization", "Api-Token ${NetworkingConstants.API_KEY}")
                        .build()).build()
                )
                .setResource(otelResource)
                .build();

            val openTelemetry: OpenTelemetry = OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal()

            tracer = openTelemetry.getTracer("android-tracer", "1.0.0")
        }

        private var tracer: Tracer? = null

        @JvmStatic
        fun getTracer(): Tracer? {
            return tracer
        }
    }

}