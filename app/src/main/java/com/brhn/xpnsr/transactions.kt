package com.brhn.xpnsr

import java.io.Serializable

enum class TransactionType {
    EARNING,
    EXPENSE
}


data class Transaction(
    val name: String,
    val date: String,
    val amount: Double,
    val type: TransactionType,
    val category: String
) : Serializable