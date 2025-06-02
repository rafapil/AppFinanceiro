package com.projetos.appfinanceiro.Adapter 

import android.view.LayoutInflater 
import android.view.ViewGroup 
import androidx.recyclerview.widget.RecyclerView 
import com.bumptech.glide.Glide 
import com.projetos.appfinanceiro.Domain.ExpenseDomain 
import com.projetos.appfinanceiro.databinding.ViewholderItemsBinding 
import java.text.DecimalFormat 

class ExpenseListAdapter(private val items: MutableList<ExpenseDomain>) : 
    RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() { 

    class ViewHolder(val binding: ViewholderItemsBinding) : RecyclerView.ViewHolder(binding.root) 

    private val formatter = DecimalFormat("###,###,###.##") 

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder( 
        ViewholderItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false) 
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { 
        val item = items[position] 
        val context = holder.itemView.context 
        val drawableResourceId = context.resources.getIdentifier(item.pic, "drawable", context.packageName) 

        with(holder.binding) { 
            titleTxt.text = item.title 
            timeTxt.text = item.time 
            priceTxt.text = "$${formatter.format(item.price)}" 
            Glide.with(context).load(drawableResourceId).into(pic) 
        }
    }

    override fun getItemCount() = items.size 
}
