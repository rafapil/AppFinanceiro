package com.projetos.appfinanceiro.telemetry

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//object DynatraceLogger {
//
//    private val client = OkHttpClient()
//    private const val API_URL = "https://phr27629.live.dynatrace.com/api/v2/logs/ingest"
//    private const val API_TOKEN = "dt0c01.N2LVKO6DB6FV6PVQ4DIRHWBG.VSTJ6STNJ4MB2QIFMUUFUWUQGVVARBD4XLKE6HBKC6PFQWJFV6PUVTPT43QDTZWD" // Substitua por variável de ambiente/secure storage
//
//    fun log(content: String, status: String = "info", serviceName: String, namespace: String) {
//        val jsonBody = """
//            [
//                {
//                    "content": "$content",
//                    "status": "$status",
//                    "service.name": "$serviceName",
//                    "service.namespace": "$namespace"
//                }
//            ]
//        """.trimIndent()
//
//        val request = Request.Builder()
//            .url(API_URL)
//            .addHeader("Content-Type", "application/json; charset=utf-8")
//            .addHeader("Authorization", "Api-Token $API_TOKEN")
//            .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
//            .build()
//
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) {
//                println("Erro ao enviar log: ${response.code} - ${response.body?.string()}")
//            }
//        }
//    }
//}


object DynatraceLogger {

    private val client = OkHttpClient()
    private const val API_URL = "https://phr27629.live.dynatrace.com/api/v2/logs/ingest"
    private const val API_TOKEN = "dt0c01.N2LVKO6DB6FV6PVQ4DIRHWBG.VSTJ6STNJ4MB2QIFMUUFUWUQGVVARBD4XLKE6HBKC6PFQWJFV6PUVTPT43QDTZWD" // Substitua pela sua chave real

    fun log(content: String, status: String = "info", serviceName: String, namespace: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonBody = """
                [
                    {
                        "content": "$content",
                        "status": "$status",
                        "service.name": "$serviceName",
                        "service.namespace": "$namespace"
                    }
                ]
            """.trimIndent()

            val request = Request.Builder()
                .url(API_URL)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Authorization", "Api-Token $API_TOKEN")
                .post(jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    println("Log enviado com sucesso: ${response.code}")
                } else {
                    println("Erro ao enviar log: ${response.code} - ${response.body?.string()}")
                }
            }
        }
    }
}


