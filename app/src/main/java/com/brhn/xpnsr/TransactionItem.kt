package com.brhn.xpnsr

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = getCategoryIcon(transaction.category)),
            contentDescription = "Transaction Image",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = transaction.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = transaction.category,
                color = Color.Gray
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "\$${String.format("%.2f", transaction.amount)}",
                color = if (transaction.type == TransactionType.EXPENSE) Color.Red else Color.Green,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = transaction.date,
                color = Color.Gray
            )
        }
    }
}
