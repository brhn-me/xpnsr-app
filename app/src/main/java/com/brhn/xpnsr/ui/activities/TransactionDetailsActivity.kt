package com.brhn.xpnsr.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.brhn.xpnsr.ui.components.TransactionDetail
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class TransactionDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transaction = intent.getSerializableExtra("TRANSACTION_DATA") as Transaction

        setContent {
            XPNSRTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TransactionDetail(transaction) {
                        finish()
                    }
                }
            }
        }
    }
}