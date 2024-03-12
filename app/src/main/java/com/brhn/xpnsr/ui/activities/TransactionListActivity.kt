package com.brhn.xpnsr.ui.activities

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.brhn.xpnsr.ui.components.TransactionList
import com.brhn.xpnsr.viewmodels.TransactionViewModel
import com.brhn.xpnsr.workers.NotificationWorker
import com.brhn.xpnsr.workers.ReminderWorker
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TransactionListActivity : ComponentActivity() {
    private val transactionViewModel: TransactionViewModel by viewModels();
    private var keepSplash = true
    private val delay = 1200L
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        setupSplashScreen(splashScreen = splashScreen)

        super.onCreate(savedInstanceState)

        schedulePeriodicWork()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d(TAG, token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        }

        setContent {
            MainScreen(transactionViewModel, applicationContext)

        }


    }

    private fun schedulePeriodicWork() {
        val workRequest1 = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.MINUTES).build()
        val workRequest2 =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(workRequest1)
        WorkManager.getInstance(this).enqueue(workRequest2)
    }

    private fun setupSplashScreen(splashScreen: androidx.core.splashscreen.SplashScreen) {
        // Replace this timer with your logic to load data on the splash screen.
        splashScreen.setKeepOnScreenCondition { keepSplash }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplash = false
        }, delay)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(transactionViewModel: TransactionViewModel, applicationContext: Context) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("XPNSR App", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "List Transactions") },
                    selected = false,
                    onClick = {
                        val intent = Intent(applicationContext, TransactionListActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        applicationContext.startActivity(intent)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Add Transaction") },
                    selected = false,
                    onClick = {
                        val intent = Intent(applicationContext, AddTransactionActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        applicationContext.startActivity(intent)
                    }
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Report") },
                    selected = false,
                    onClick = {
                        val intent = Intent(applicationContext, ReportActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        applicationContext.startActivity(intent)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Locations") },
                    selected = false,
                    onClick = {
                        val intent = Intent(applicationContext, MapViewActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        applicationContext.startActivity(intent)
                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Profile Photo select") },
                    selected = false,
                    onClick = {

                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Demo (Video Playback)") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )

                NavigationDrawerItem(
                    label = { Text(text = "Sensor Use") },
                    selected = false,
                    onClick = {

                    }
                )

                NavigationDrawerItem(
                    label = { Text(text = "API Call") },
                    selected = false,
                    onClick = {

                    }
                )
                // ...other drawer items
            }
        }
    ) {


        Scaffold(

            topBar = {
                TopAppBar(
                    title = { Text("XPNSR App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = { padding ->
                BodyContent(Modifier.padding(padding))
                val transactions =
                    transactionViewModel.allTransactions.observeAsState(initial = emptyList()).value
                TransactionList(
                    transactions = transactions,
                    context = applicationContext
                )
            }
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {

}


