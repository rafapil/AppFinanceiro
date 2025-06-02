package com.projetos.appfinanceiro.Domain 

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BudgetDomain( 
    val title: String = "", 
    val price: Double = 0.0, 
    val percent: Double = 0.0 
) : Parcelable