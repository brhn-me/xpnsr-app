package com.brhn.xpnsr.activities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlin.random.Random


class ReportActivity : BaseActivity() {

    @Composable
    override fun ScreenContent(modifier: Modifier) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Report()
        }
    }

    override fun getAppBarTitle(): String {
        return "Report"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Report() {
        val context = LocalContext.current
        val scrollState = rememberScrollState()

        // Dynamically generated values for the last 12 months
        val monthlyData = generateLast12MonthsData()


        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Spacer(modifier = Modifier.height(48.dp))
                Text("Expenses by Categories", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                PieChart(
                    slices = listOf(
                        PieChartData.Slice(
                            "Utility Bills",
                            Random.nextFloat() * 100,
                            Color(0xFF5F0A87)
                        ),
                        PieChartData.Slice(
                            "Groceries",
                            Random.nextFloat() * 100,
                            Color(0xFF20BF55)
                        ),
                        PieChartData.Slice(
                            "Home Rent",
                            Random.nextFloat() * 100,
                            Color(0xFFEC9F05)
                        ),
                        PieChartData.Slice("Others", Random.nextFloat() * 100, Color(0xFFF53844))
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Monthly Report", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                BarChart(monthlyData = monthlyData)
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun generateLast12MonthsData(): List<MonthlyFinanceData> {
        val currentMonth = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MMM ''yy")
        return List(12) { index ->
            val month = currentMonth.minusMonths(index.toLong())
            MonthlyFinanceData(
                month.format(formatter),
                Random.nextInt(1000, 5000).toFloat(),
                Random.nextInt(5000, 10000).toFloat()
            )
        }.reversed()
    }


}

data class PieChartData(val slices: List<Slice>) {
    data class Slice(val label: String, val value: Float, val color: Color)
}

data class MonthlyFinanceData(val month: String, val expenses: Float, val earnings: Float)


@Composable
fun PieChart(slices: List<PieChartData.Slice>, modifier: Modifier = Modifier) {
    val total = slices.map { it.value }.sum()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        // Adjusting the layout for legends to avoid overlapping
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            slices.forEach { slice ->
                val percentage = ((slice.value / total) * 100).toInt()
                Row(
                    modifier = Modifier
                        .padding(vertical = 2.dp), // Adjust vertical padding for spacing
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = slice.color)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("${slice.label}: $percentage%", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}


@Composable
fun BarChart(monthlyData: List<MonthlyFinanceData>, modifier: Modifier = Modifier) {
    val maxAmount = max(monthlyData.maxOf { it.expenses }, monthlyData.maxOf { it.earnings })

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjust the height as needed
        ) {
            val spaceBetween = 10.dp.toPx() // Space between bars
            val totalBarSpace = size.width - (monthlyData.size * spaceBetween)
            val barWidth =
                totalBarSpace / (monthlyData.size * 2) // Divide by 2 because we have two bars per month

            drawBars(this, monthlyData, maxAmount, barWidth, spaceBetween)
        }
    }
}

private fun drawBars(
    drawScope: DrawScope,
    monthlyData: List<MonthlyFinanceData>,
    maxAmount: Float,
    barWidth: Float,
    spaceBetween: Float
) {
    monthlyData.forEachIndexed { index, data ->
        val expensesBarHeight = (data.expenses / maxAmount) * drawScope.size.height
        val earningsBarHeight = (data.earnings / maxAmount) * drawScope.size.height

        // Draw expenses bar
        drawScope.drawRect(
            color = Color.Red,
            topLeft = Offset(
                x = (index * (barWidth * 2 + spaceBetween)),
                y = drawScope.size.height - expensesBarHeight
            ),
            size = Size(width = barWidth, height = expensesBarHeight)
        )

        // Draw earnings bar
        drawScope.drawRect(
            color = Color.Green,
            topLeft = Offset(
                x = (index * (barWidth * 2 + spaceBetween)) + barWidth,
                y = drawScope.size.height - earningsBarHeight
            ),
            size = Size(width = barWidth, height = earningsBarHeight)
        )
    }
}