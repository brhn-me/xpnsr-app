package com.brhn.xpnsr.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.brhn.xpnsr.ui.components.TransactionList
import com.brhn.xpnsr.data.getSampleTransactions
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class TransactionListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPNSRTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                val intent = Intent(this, AddTransactionActivity::class.java)
                                startActivity(intent)
                            }) {
                                Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
                            }
                        }
                    ) { innerPadding ->
                        TransactionList(
                            transactions = getSampleTransactions(),
                            context = this@TransactionListActivity,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}