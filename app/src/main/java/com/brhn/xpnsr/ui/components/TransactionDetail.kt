package com.brhn.xpnsr.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brhn.xpnsr.R
import com.brhn.xpnsr.ui.activities.ReportActivity
import com.brhn.xpnsr.models.Transaction


@Composable
fun TransactionDetail(transaction: Transaction, onBack: () -> Unit) {

    val context = LocalContext.current

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

        Button(
            onClick = {
                val intent = Intent(context, ReportActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Go to Report")
        }
    }
}
