package com.projetos.appfinanceiro.Activity

import TelemetryLogger
import android.content.Intent
import android.os.Bundle 
import androidx.appcompat.app.AppCompatActivity
//import androidx.privacysandbox.tools.core.generator.build
import com.dynatrace.android.agent.Dynatrace
import com.dynatrace.android.agent.conf.DynatraceConfigurationBuilder
import com.projetos.appfinanceiro.databinding.ActivityIntroBinding
import io.opentelemetry.api.common.Attributes

//import java.util.jar.Attributes

//import io.opentelemetry.api.common.Attributes

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // esse ponto configura o oneAgent manualmente
//        Dynatrace.startup(this, DynatraceConfigurationBuilder("3b01ca75-d471-448a-9489-7332dd00a157", "https://bf05427jqz.bf.dynatrace.com/mbeacon")
//            // configuracoes adicionais
//            .withCrashReporting(true)
//            .buildConfiguration())

        // Example log with attributes
        val attributes = Attributes.builder()
            .put("button.name", "login_button")
            .put("screen.name", "login_screen")
            .build()

        TelemetryLogger.logger.logRecordBuilder()
            .setBody("Mensagem de log de teste")
//            .setAttribute(attributes)
            .setSeverity(io.opentelemetry.api.logs.Severity.DEBUG)
            .emit()


        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
