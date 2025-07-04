package com.projetos.appfinanceiro.integration.cielo

import com.projetos.appfinanceiro.integration.config.NetworkingConstants
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.coroutines.channels.Channel


@Serializable
data class LogSerializable(
    val traceId: String? = null,
    val spanId: String? = null,
    val parentId: String? = null,
    val logType: String,
    val acronym: String,
    val logLevel: String,
    @SerialName("service.name") val serviceName: String,
    @SerialName("service.namespace") val namespace: String,
    val operation: String,
    val content: String,
    val duration: Long? = null,
    val value: Double? = null,
    val extra: ExtraInfoDto? = null
)

@Serializable
data class ExtraInfoDto(
    val userId: String? = null,
    val transactionId: String? = null,
    val statusCode: Int? = null,
    val exception: String? = null,
    val errorMessage: String? = null,
    val errorStack: List<String>? = null
)


object CieloLogExporter {

    private val client = OkHttpClient()
    private const val API_URL = NetworkingConstants.BASE_URL_LOGS
    private const val API_TOKEN = "Api-Token ${NetworkingConstants.API_KEY}"
    private val json = Json { encodeDefaults = false; ignoreUnknownKeys = true }

    // Canal com buffer ilimitado (pode trocar por BUFFERED ou conflated)
    private val logChannel = Channel<CieloLog>(Channel.UNLIMITED)

    init {
        // Inicializa o consumo da fila assim que o app sobe
        CoroutineScope(Dispatchers.IO).launch {
            for (log in logChannel) {
                sendLogWithRetry(log)
            }
        }
    }

    //    fun log(log: CieloLog) {
//        CoroutineScope(Dispatchers.IO).launch {
//            logChannel.send(log)
//        }
//    }
    fun log(log: CieloLog) {
        CoroutineScope(Dispatchers.IO).launch {
            val enriched = enrichWithCurrentSpan(log)
            logChannel.send(enriched)
        }
    }


    private suspend fun sendLogWithRetry(log: CieloLog, tentativas: Int = 3) {
        val logSerializable = LogSerializable(
            traceId = log.traceId,
            spanId = log.spanId,
            parentId = log.parentId,
            logType = log.logType.name,
            acronym = log.acronym,
            logLevel = log.level.name,
            serviceName = log.serviceName,
            namespace = log.namespace.name,
            operation = log.operation,
            content = log.content,
            duration = log.duration,
            value = log.value,
            extra = log.extra?.let {
                ExtraInfoDto(
                    userId = it.userId,
                    transactionId = it.transactionId,
                    statusCode = it.statusCode,
                    exception = it.exception,
                    errorMessage = it.errorMessage,
                    errorStack = it.errorStack
                )
            }
        )

        val requestBody = json.encodeToString(listOf(logSerializable))
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", API_TOKEN)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .post(requestBody)
            .build()

        repeat(tentativas) { tentativa ->
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        println("Log enviado com sucesso")
                        return
                    } else {
                        println("Tentativa ${tentativa + 1}: erro HTTP ${response.code}")
                    }
                }
            } catch (e: Exception) {
                println("Tentativa ${tentativa + 1} falhou: ${e.localizedMessage}")
            }

            kotlinx.coroutines.delay(1000L * (tentativa + 1))
        }

        // Fallback final
        salvarLogLocal(log)
    }

    private fun salvarLogLocal(log: CieloLog) {
        println("Fallback: salvando log local -> $log")
        // Aqui você pode salvar no SharedPreferences, SQLite, Room, arquivo etc.
    }

    /*
    * Aqui faz o inriquecimento dos dados
    * */
    private fun enrichWithCurrentSpan(log: CieloLog): CieloLog {
        val currentSpan = io.opentelemetry.api.trace.Span.current()
        val context = currentSpan.spanContext

        if (!context.isValid) return log

        return log.copy(
            traceId = log.traceId ?: context.traceId,
            spanId = log.spanId ?: context.spanId,
            parentId = log.parentId // OpenTelemetry não fornece `parentId` diretamente.
            // Se necessário, você pode manter o valor existente ou estender para controlar manualmente
        )
    }


}





