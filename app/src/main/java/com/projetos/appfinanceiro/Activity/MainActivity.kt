package com.projetos.appfinanceiro.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dynatrace.android.agent.Dynatrace
import com.dynatrace.android.agent.conf.DynatraceConfigurationBuilder
import com.projetos.appfinanceiro.Adapter.ExpenseListAdapter
import com.projetos.appfinanceiro.ViewModel.MainViewModel
import com.projetos.appfinanceiro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // esse ponto configura o oneAgent manualmente (melhor opcao)
        Dynatrace.startup(this, DynatraceConfigurationBuilder("3b01ca75-d471-448a-9489-7332dd00a157", "https://bf05427jqz.bf.dynatrace.com/mbeacon")
            // additional configuration
            .buildConfiguration())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        setVariable()
        observeData()
    }

    private fun setVariable() {
        binding.cardBtn.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        binding.view1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.view1.isNestedScrollingEnabled = false
    }

    private fun observeData() {
        mainViewModel.expenseList.observe(this, Observer { items ->
            binding.view1.adapter = ExpenseListAdapter(items)
        })
    }
}
