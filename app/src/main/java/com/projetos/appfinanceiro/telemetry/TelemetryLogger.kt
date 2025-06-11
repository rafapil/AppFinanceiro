import io.opentelemetry.api.logs.Logger
import io.opentelemetry.api.logs.LoggerProvider
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter
import java.util.concurrent.TimeUnit

//object TelemetryLogger {
//
//    private val loggerProvider: LoggerProvider
//    val logger: Logger
//
//    init {
//        val exporter = OtlpGrpcLogRecordExporter.builder()
//            .setEndpoint("https://phr27629.live.dynatrace.com/api/v2/otlp/v1/logs") // ou sua URL de OTLP
//            .addHeader("Authorization"," Api-Token dt0c01.N2LVKO6DB6FV6PVQ4DIRHWBG.VSTJ6STNJ4MB2QIFMUUFUWUQGVVARBD4XLKE6HBKC6PFQWJFV6PUVTPT43QDTZWD")
//            .build()
//
//        val logProcessor = BatchLogRecordProcessor.builder(exporter).build()
//
//        loggerProvider = SdkLoggerProvider.builder()
//            .addLogRecordProcessor(logProcessor)
//            .build()
//
//        logger = loggerProvider.loggerBuilder("meu-app-logger").build()
//    }
//}

object TelemetryLogger {

    private val sdkLoggerProvider: SdkLoggerProvider by lazy {
        val exporter = OtlpGrpcLogRecordExporter.builder()
            .setEndpoint("https://phr27629.live.dynatrace.com/api/v2/otlp/v1/logs") // ajuste para seu collector OTLP
//            .addHeader("Authorization"," Api-Token token")
            .setTimeout(10, TimeUnit.SECONDS)
            .build()


        val processor = BatchLogRecordProcessor.builder(exporter)
            .setScheduleDelay(100, TimeUnit.MILLISECONDS) // Optional: Configure batching
            .build()

        SdkLoggerProvider.builder()
            .addLogRecordProcessor(processor)
            .build()
    }

    val logger: Logger by lazy {
        sdkLoggerProvider.loggerBuilder("android-app-logger").build()
    }
}


