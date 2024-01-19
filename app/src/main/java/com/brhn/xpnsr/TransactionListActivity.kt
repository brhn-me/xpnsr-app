package com.brhn.xpnsr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class TransactionListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPNSRTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TransactionList(getSampleTransactions())
                }
            }
        }
    }
}

@Composable
fun TransactionList(transactions: List<Transaction>) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

// Sample data generator
fun getSampleTransactions(): List<Transaction> {
    return listOf(
        Transaction("Groceries", "01/01/2024", 50.0),
        // Add more sample transactions here
    )
}

// Data class representing a transaction
data class Transaction(
    val name: String,
    val date: String,
    val amount: Double
)
