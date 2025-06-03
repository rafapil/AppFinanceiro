package com.projetos.appfinanceiro.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dynatrace.android.agent.Dynatrace
import com.projetos.appfinanceiro.Adapter.ExpenseListAdapter
import com.projetos.appfinanceiro.ViewModel.MainViewModel
import com.projetos.appfinanceiro.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.debtorBtn.setOnClickListener {
            sendDataDynatrace()
        }

        binding.addCardBtn.setOnClickListener {
            sendEventToDynatrace()
        }

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

    // aqui é enviado um evento para o Dynatrace
    private fun sendEventToDynatrace() {
        val action = Dynatrace.enterAction("Start App")
        action.reportEvent("Foi realizado o login na aplicação")
    }

    // função estatica para enviar eventos do tipo Business Event para o Dynatrace
    private fun sendDataDynatrace() {
        try {
            JSONObject().apply {
                put("event.name", "Debtor action selected")
                put("screen", "accesss_debtor_screen")
                put("product", "Danube Anna Hotel")
                put("amount", 358.35)
                put("currency", "USD")
                put("reviewScore", 4.8)
                put("arrivalDate", "2022-11-05")
                put("departureDate", "2022-11-15")
                put("journeyDuration", 10)
                put("adultTravelers", 2)
                put("childrenTravelers", 0)
            }.also { jsonObject->
                Dynatrace.sendBizEvent("com.easytravel.funnel.booking-finished", jsonObject)
            }
        } catch (e: JSONException) {
            // handle exception
        }
    }


}
