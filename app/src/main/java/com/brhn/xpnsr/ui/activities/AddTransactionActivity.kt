package com.brhn.xpnsr.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.brhn.xpnsr.data.db.AppDatabase
import com.brhn.xpnsr.data.repository.TransactionRepository
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType
import com.brhn.xpnsr.ui.components.TransactionForm
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class AddTransactionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPNSRTheme {
                // Assuming you have a method getDatabase() that returns the AppDatabase instance
                val appDatabase = AppDatabase.getDatabase(context = this)
                val transactionDao = appDatabase.transactionDao()
                val transactionRepository = TransactionRepository(transactionDao)

                Surface(color = MaterialTheme.colorScheme.background) {
                    TransactionForm(
                        onBack = { finish() },
                        transactionRepository = transactionRepository,
                        onTransactionAdded = { finish() }
                    )
                }
            }
        }
    }
}