package com.brhn.xpnsr.ui.components;

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brhn.xpnsr.data.getCategoryIcon
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType

@Composable
fun TransactionList(
    transactions: List<Transaction>,
    context: Context,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(transactions) { transaction ->
            TransactionItem(transaction) {
//                val intent = Intent(context, TransactionDetailsActivity::class.java)
//                intent.putExtra("TRANSACTION_DATA", transaction)
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
            }
        }
    }
}


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

