package com.brhn.xpnsr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your actual image resource
            contentDescription = "Transaction Image",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column {
            Text(text = transaction.name)
            Text(text = transaction.date)
            Text(text = "Amount: \$${transaction.amount}")
        }
    }
}
