package com.projetos.appfinanceiro.integration.config

object NetworkingConstants {
    const val BASE_URL_LOGS = "https://{{env_dyna}}.live.dynatrace.com/api/v2/logs/ingest" // o endpoint de logs otel é diferente e nao esta funcionando
    const val BASE_URL_TRACE = "https://{{env_dyna}}.live.dynatrace.com/api/v2/otlp/v1/traces"
    const val BASE_URL_METRICS = "https://{{env_dyna}}.live.dynatrace.com/api/v2/otlp/v1/metrics" // nao esta implementado
    const val API_KEY = "{{seu_token}}"
    const val APP_ID = ""
    const val BEACON_URL = ""
}