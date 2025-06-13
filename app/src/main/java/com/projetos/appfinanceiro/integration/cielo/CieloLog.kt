package com.projetos.appfinanceiro.integration.cielo

import com.projetos.appfinanceiro.logging.LogLevel
import com.projetos.appfinanceiro.logging.LogType
import com.projetos.appfinanceiro.logging.Namespace

data class CieloLog(
    val logType: LogType,
    val acronym: String,
    val level: LogLevel,
    val serviceName: String,
    val namespace: Namespace,
    val operation: String,
    val content: String,
    val duration: Long? = null,
    val value: Double? = null,
    val extra: CieloExtraInfo? = null
)
