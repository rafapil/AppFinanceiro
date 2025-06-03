package com.projetos.appfinanceiro.Activity

import android.content.Intent 
import android.os.Bundle 
import androidx.appcompat.app.AppCompatActivity
import com.dynatrace.android.agent.Dynatrace
import com.dynatrace.android.agent.conf.DynatraceConfigurationBuilder
import com.projetos.appfinanceiro.databinding.ActivityIntroBinding 

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // esse ponto configura o oneAgent manualmente
        Dynatrace.startup(this, DynatraceConfigurationBuilder("<apllicationId>", "<url de comunicação com a API do Dynatrace>")
            // configuracoes adicionais
            .withCrashReporting(true)
            .buildConfiguration())

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
