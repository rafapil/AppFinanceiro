package com.projetos.appfinanceiro.ViewModel 

import androidx.lifecycle.LiveData 
import androidx.lifecycle.MutableLiveData 
import androidx.lifecycle.ViewModel 
import com.projetos.appfinanceiro.Domain.BudgetDomain 
import com.projetos.appfinanceiro.Domain.ExpenseDomain 
import com.projetos.appfinanceiro.Repository.MainRepository 

class MainViewModel(private val repository: MainRepository = MainRepository()) : ViewModel() { 

    private val _expenseList = MutableLiveData(repository.items) 
    val expenseList: LiveData<MutableList<ExpenseDomain>> = _expenseList 

    private val _budgetList = MutableLiveData(repository.budget) 
    val budgetList: LiveData<MutableList<BudgetDomain>> = _budgetList 
}
