package com.projetos.appfinanceiro.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.projetos.appfinanceiro.databinding.ActivityIntroBinding
import com.projetos.appfinanceiro.integration.dynatrace.DynatraceConfigRUM
import com.projetos.appfinanceiro.integration.otel.tracer.GlobalExceptionInterceptor
import com.projetos.appfinanceiro.integration.otel.tracer.TracerOpenTelemetryUtil
import com.projetos.appfinanceiro.integration.otel.tracer.ActivityNavigationTracer


class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

//    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynatraceConfigRUM.initialize(application)
        // Add the following line to initialize OpenTelemetry.
        TracerOpenTelemetryUtil.init()

        // initiazer global tracer error otel
        GlobalExceptionInterceptor.initialize(application)

        // initiazer global tracker navigation (funcional porem consegui resultado com api 29+)
        registerActivityLifecycleCallbacks(ActivityNavigationTracer)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
