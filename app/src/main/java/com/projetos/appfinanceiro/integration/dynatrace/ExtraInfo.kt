package com.projetos.appfinanceiro.integration.dynatrace

data class ExtraInfo(
    val userId: String? = null,
    val transactionId: String? = null,
    val statusCode: Int? = null,
    val exception: String? = null,
    val errorMessage: String? = null,
    val errorStack: List<String>? = null
)
