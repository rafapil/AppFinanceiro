package com.projetos.appfinanceiro.integration.dynatrace

import android.app.Application
import com.dynatrace.android.agent.Dynatrace
import com.dynatrace.android.agent.conf.DynatraceConfigurationBuilder
import com.projetos.appfinanceiro.integration.config.NetworkingConstants
import okhttp3.OkHttpClient

object DynatraceConfigRUM {

    fun initialize(application: Application) {
        val config = DynatraceConfigurationBuilder(NetworkingConstants.APP_ID, NetworkingConstants.BEACON_URL)
            .withCrashReporting(true)
            .withActivityMonitoring(true)
            .withOkHttpClient(OkHttpClient())
            .buildConfiguration()
        Dynatrace.startup(application, config)
    }
}