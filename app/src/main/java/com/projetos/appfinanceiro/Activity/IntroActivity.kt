package com.projetos.appfinanceiro.Activity

import android.content.Intent 
import android.os.Bundle 
import androidx.appcompat.app.AppCompatActivity 
import com.projetos.appfinanceiro.databinding.ActivityIntroBinding 

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
