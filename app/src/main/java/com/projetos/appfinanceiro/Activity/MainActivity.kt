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
import com.projetos.appfinanceiro.integration.cielo.CieloLog
import com.projetos.appfinanceiro.integration.cielo.CieloExtraInfo
import com.projetos.appfinanceiro.integration.cielo.CieloLogExporter
import com.projetos.appfinanceiro.logging.LogLevel
import com.projetos.appfinanceiro.logging.LogType
import com.projetos.appfinanceiro.logging.Namespace
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
            sendDataLog()
        }

        binding.getCashBtn.setOnClickListener {
            sendDataDynatrace()
        }

        binding.addCardBtn.setOnClickListener {
            try {
                throw IllegalStateException("Erro forçado para teste")
            } catch (e: Exception) {
                sendDataLogError(e)
            }
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

    private fun sendDataLog() {
        CieloLogExporter.log(
            CieloLog(
                logType = LogType.TBS,
                acronym = "xpto",
                level = LogLevel.WARN,
                serviceName = "AppFinanceiro",
                namespace = Namespace.PRD,
                operation = "MainActivity",
                content = "Falha ao buscar saldo",
                duration = 617,
                value = 250.99,
                extra = CieloExtraInfo(
                    userId = "user-001",
                    transactionId = "txn-987654",
                    statusCode = 404,
                    exception = "Error in application",
                    errorMessage = "fail to connect",
                    errorStack = listOf()
                )
            )
        )
    }

    private fun sendDataLogError(error: Exception) {
        val stackErrorString: List<String> = error.stackTrace.map { it.toString() }
        CieloLogExporter.log(
            CieloLog(
                logType = LogType.TBS,
                acronym = "xpto",
                level = LogLevel.ERROR,
                serviceName = "AppFinanceiro",
                namespace = Namespace.PRD,
                operation = "MainActivity",
                content = "Falha ao buscar saldo",
                duration = 617,
                value = 250.99,
                extra = CieloExtraInfo(
                    userId = "user-001",
                    transactionId = "txn-987654",
                    statusCode = 404,
                    exception = "Error in application",
                    errorMessage = error.message,
                    errorStack = stackErrorString
                )
            )
        )
    }

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
