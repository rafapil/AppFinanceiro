package com.projetos.appfinanceiro.Domain.model

data class LogExtraInfo(
    val userId: String? = null,
    val transactionId: String? = null,
    val statusCode: Int? = null,
    val exception: String? = null,
    val errorMessage: String? = null,
    val errorStack: List<String>? = null
)
