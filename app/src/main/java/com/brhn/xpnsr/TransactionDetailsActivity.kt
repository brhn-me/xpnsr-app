package com.brhn.xpnsr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class TransactionDetailActivity : ComponentActivity() {
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

@Composable
fun TransactionDetail(transaction: Transaction, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "Transaction Details",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text("Name: ${transaction.name}", fontSize = 18.sp)
        Text("Date: ${transaction.date}", fontSize = 18.sp)
        Text("Amount: \$${transaction.amount}", fontSize = 18.sp)
        Text("Type: ${transaction.type}", fontSize = 18.sp)
        Text("Category: ${transaction.category}", fontSize = 18.sp)

        Image(
            painter = painterResource(id = R.drawable.receipt_sample),
            contentDescription = "Transaction Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(bottom = 16.dp)
        )
    }
}
