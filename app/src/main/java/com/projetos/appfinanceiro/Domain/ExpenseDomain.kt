package com.projetos.appfinanceiro.Domain 

import android.os.Parcelable 
import kotlinx.parcelize.Parcelize

@Parcelize 
data class ExpenseDomain( 
    val title: String = "", 
    val price: Double = 0.0, 
    val pic: String = "", 
    val time: String = "" 
) : Parcelable