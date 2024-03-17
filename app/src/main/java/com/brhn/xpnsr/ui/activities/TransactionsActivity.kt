package com.brhn.xpnsr.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.brhn.xpnsr.data.db.AppDatabase
import com.brhn.xpnsr.data.getCategoryIcon
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType
import com.brhn.xpnsr.data.viewmodels.TransactionsVM
import com.brhn.xpnsr.workers.NotificationWorker
import com.brhn.xpnsr.workers.ReminderWorker
import java.util.concurrent.TimeUnit

class TransactionsActivity : BaseActivity() {
    private val transactionsViewModel: TransactionsVM by viewModels()
    private var keepSplash = true
    private val delay = 1200L

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        setupSplashScreen(splashScreen = splashScreen)
        AppDatabase.prepopulateIfEmpty(this)
        schedulePeriodicWork()
        super.onCreate(savedInstanceState)
    }


    @Composable
    override fun ScreenContent(modifier: Modifier) {
        val transactions =
            transactionsViewModel.transactions.observeAsState(initial = emptyList()).value
        TransactionList(
            transactions = transactions, context = applicationContext
        )
    }

    override fun getAppBarTitle(): String {
        return "Transactions"
    }

    private fun schedulePeriodicWork() {
        val workRequest1 = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.MINUTES).build()
        val workRequest2 =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(workRequest1)
        WorkManager.getInstance(this).enqueue(workRequest2)
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        splashScreen.setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplash = false
        }, delay)
    }

    @Composable
    fun TransactionList(
        transactions: List<Transaction>, context: Context, modifier: Modifier = Modifier
    ) {
        LazyColumn(modifier = modifier) {
            items(transactions) { transaction ->
                TransactionItem(transaction, applicationContext)
            }
        }
    }


    @Composable
    fun TransactionItem(transaction: Transaction, context: Context) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, TransactionActivity::class.java)
                    intent.putExtra(
                        "TRANSACTION_ID",
                        transaction.id
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
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
                    text = transaction.name, fontWeight = FontWeight.Bold
                )
                Text(
                    text = transaction.category, color = Color.Gray
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
                    text = transaction.date, color = Color.Gray
                )
            }
        }
    }
}