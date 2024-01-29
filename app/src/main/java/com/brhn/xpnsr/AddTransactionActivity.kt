package com.brhn.xpnsr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class AddTransactionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XPNSRTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TransactionForm {
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionForm(onBack: () -> Unit) {
    val (name, setName) = remember { mutableStateOf("") }
    val (date, setDate) = remember { mutableStateOf("") }
    val (amount, setAmount) = remember { mutableStateOf("") }
    val (type, setType) = remember { mutableStateOf("") }
    val (category, setCategory) = remember { mutableStateOf("") }

    val context = LocalContext.current

    Column {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = {
                    context.startActivity(Intent(context, TransactionListActivity::class.java))
                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Transaction List")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    context.startActivity(Intent(context, ReportActivity::class.java))
                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("View Report")
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Add Transaction", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = setName,
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = date,
                onValueChange = setDate,
                label = { Text("Date") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = setAmount,
                label = { Text("Amount") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = type,
                onValueChange = setType,
                label = { Text("Type (Earning/Expense)") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = category,
                onValueChange = setCategory,
                label = { Text("Category") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
            }) {
                Text("Add Transaction")
            }
        }
    }
}

