package com.projetos.appfinanceiro.integration.config

object NetworkingConstants {
    const val BASE_URL_LOGS = "https://{your-environment-id}.live.dynatrace.com/api/v2/logs/ingest" // o endpoint de logs otel é diferente
    const val BASE_URL_TRACE = "https://{your-environment-id}.live.dynatrace.com/api/v2/otlp/v1/traces"
    const val BASE_URL_METRICS = "https://{your-environment-id}.live.dynatrace.com/api/v2/otlp/v1/metrics"
    const val API_KEY = "api_key"
    const val APP_ID = ""
    const val BEACON_URL = ""
}