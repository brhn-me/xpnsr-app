package com.brhn.xpnsr.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.brhn.xpnsr.data.db.AppDatabase
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType
import com.brhn.xpnsr.ui.components.TransactionForm
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class AddTransactionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPNSRTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TransactionForm {

                        AppDatabase.getDatabase(this).transactionDao().insert(Transaction(0, "Groceries", "01/01/2024", 50.0, TransactionType.EXPENSE, "Groceries"))
                        finish()
                    }
                }
            }
        }
    }
}