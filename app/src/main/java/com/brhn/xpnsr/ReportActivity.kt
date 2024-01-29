package com.brhn.xpnsr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.brhn.xpnsr.ui.theme.XPNSRTheme

class ReportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            XPNSRTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Report() {
                        finish()
                    }
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent = Intent(this, TransactionListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}

@Composable
fun Report(onBack: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        PieChart(
            slices = listOf(
                PieChartData.Slice("Utility Bills", 15f, Color(0xFF5F0A87)),
                PieChartData.Slice("Groceries", 30f, Color(0xFF20BF55)),
                PieChartData.Slice("Home Rent", 40f, Color(0xFFEC9F05)),
                PieChartData.Slice("Others", 10f, Color(0xFFF53844))
            )
        )

    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)) {
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
                context.startActivity(Intent(context, AddTransactionActivity::class.java))
            },
            modifier = Modifier
                .weight(1f)
        ) {
            Text("Add Transaction")
        }
    }

}

data class PieChartData(val slices: List<Slice>) {
    data class Slice(val label: String, val value: Float, val color: Color)
}


@Composable
fun PieChart(slices: List<PieChartData.Slice>) {
    val total = slices.map { it.value }.sum()
    Column {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            var startAngle = 0f

            slices.forEach { slice ->
                val sweepAngle = (slice.value / total) * 360f
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset.Zero,
                    size = size
                )
                startAngle += sweepAngle
            }
        }
        slices.forEach { slice ->
            val percentage = ((slice.value / total) * 100).toInt()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color = slice.color)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("${slice.label}: $percentage%")
            }
        }
    }
}
