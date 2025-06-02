package com.projetos.appfinanceiro.Adapter 

import android.view.LayoutInflater 
import android.view.ViewGroup 
import androidx.core.content.ContextCompat 
import androidx.recyclerview.widget.RecyclerView 
import com.projetos.appfinanceiro.Domain.BudgetDomain 
import com.projetos.appfinanceiro.R 
import com.projetos.appfinanceiro.databinding.ViewholderBudgetBinding 
import java.text.DecimalFormat 

class ReportListAdapter(private val items: MutableList<BudgetDomain>) : 
    RecyclerView.Adapter<ReportListAdapter.ViewHolder>() { 

    class ViewHolder(val binding: ViewholderBudgetBinding) : RecyclerView.ViewHolder(binding.root) 

    private val formatter = DecimalFormat("###,###,###,###") 

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder( 
        ViewholderBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false) 
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { 
        val item = items[position] 
        val context = holder.itemView.context 
        val color = if (position % 2 == 1) R.color.blue else R.color.pink 

        with(holder.binding) { 
            titleTxt.text = item.title 
            percentTxt.text = "%${item.percent}" 
            priceTxt.text = "$${formatter.format(item.price)} /Month" 
            circularProgressBar.apply { 
                progress = item.percent.toFloat() 
                progressBarColor = ContextCompat.getColor(context, color) 
                percentTxt.setTextColor(progressBarColor) 
            }
        }
    }

    override fun getItemCount() = items.size 
}
