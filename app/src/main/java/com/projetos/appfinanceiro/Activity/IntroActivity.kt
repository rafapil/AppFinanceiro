package com.projetos.appfinanceiro.Activity

import android.content.Intent
import android.os.Bundle 
import androidx.appcompat.app.AppCompatActivity
import com.projetos.appfinanceiro.databinding.ActivityIntroBinding
import com.projetos.appfinanceiro.integration.dynatrace.DynatraceConfigRUM
import com.projetos.appfinanceiro.integration.otel.GlobalExceptionInterceptor
import com.projetos.appfinanceiro.integration.otel.OpenTelemetryUtil


class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynatraceConfigRUM.initialize(application)
        // Add the following line to initialize OpenTelemetry.
        OpenTelemetryUtil.init()

        // initiazer global tracer error otel
        GlobalExceptionInterceptor.initialize(application)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
