package com.brhn.xpnsr.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.brhn.xpnsr.ui.components.TransactionList
import com.brhn.xpnsr.data.getSampleTransactions
import com.brhn.xpnsr.ui.theme.XPNSRTheme
import com.brhn.xpnsr.viewmodels.TransactionViewModel

class TransactionListActivity : ComponentActivity() {
    private val transactionViewModel: TransactionViewModel by viewModels();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //transactionViewModel.initializeDatabase()

        setContent {
            val transactions =
                transactionViewModel.allTransactions.observeAsState(initial = emptyList()).value
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
                            transactions = transactions,
                            context = this@TransactionListActivity,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}