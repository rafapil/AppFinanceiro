package com.projetos.appfinanceiro.Repository 

import com.projetos.appfinanceiro.Domain.BudgetDomain 
import com.projetos.appfinanceiro.Domain.ExpenseDomain 

class MainRepository { 
    val items = mutableListOf( 
        ExpenseDomain("Restaurante", 473.12, "img1", "17 jun 2024 19:15"), 
        ExpenseDomain("Cafeteria", 67.82, "img2", "16 jun 2024 13:57"), 
        ExpenseDomain("Salgados", 37.82, "img2", "16 jun 2024 13:57"), 
        ExpenseDomain("Cinema", 33.47, "img3", "16 jun 2024 20:45"), 
        ExpenseDomain("Restaurante", 241.12, "img1", "15 jun 2024 22:18") 
    )

    val budget = mutableListOf( 
        BudgetDomain("Empréstimo para casa", 2200.0, 80.8), 
        BudgetDomain("Subscrição", 1200.0, 10.0), 
        BudgetDomain("Outros", 1400.0, 10.0), 
        BudgetDomain("Empréstimo de carro", 600.0, 30.0) 
    )
}
