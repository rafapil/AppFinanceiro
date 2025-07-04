package com.projetos.appfinanceiro.Activity

import android.os.Bundle  
import android.view.WindowManager  
import androidx.activity.viewModels  
import androidx.appcompat.app.AppCompatActivity  
import androidx.lifecycle.Observer  
import androidx.recyclerview.widget.LinearLayoutManager  
import com.projetos.appfinanceiro.Adapter.ReportListAdapter  
import com.projetos.appfinanceiro.ViewModel.MainViewModel  
import com.projetos.appfinanceiro.databinding.ActivityReportBinding
import com.projetos.appfinanceiro.integration.otel.TraceHelper

class ReportActivity : AppCompatActivity() { 

    private lateinit var binding: ActivityReportBinding 
    private val mainViewModel: MainViewModel by viewModels() 

    override fun onCreate(savedInstanceState: Bundle?) { 
        super.onCreate(savedInstanceState)

        val span = TraceHelper.createChildSpanFromIntent(
            intent,
            spanName = "ReportActivity: onCreate",
            additionalAttributes = mapOf("screen" to "ReportActivity")
        )

        span?.makeCurrent().use {
            // lógica da activity aqui
            binding = ActivityReportBinding.inflate(layoutInflater)
            setContentView(binding.root)

            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            initRecyclerView()
            setVariable()
            observeData()
        }

        span?.end()


    }

    private fun setVariable() { 
        binding.backBtn.setOnClickListener { finish() } 
    }

    private fun initRecyclerView() { 
        binding.view2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) 
        binding.view2.isNestedScrollingEnabled = false 
    }

    private fun observeData() { 
        mainViewModel.budgetList.observe(this, Observer { items -> 
            binding.view2.adapter = ReportListAdapter(items) 
        })
    }
}
