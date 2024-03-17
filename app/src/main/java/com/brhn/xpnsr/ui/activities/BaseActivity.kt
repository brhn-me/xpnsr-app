package com.brhn.xpnsr.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(applicationContext = this, title = getAppBarTitle())
        }
    }


    @Composable
    abstract fun ScreenContent(modifier: Modifier)

    abstract fun getAppBarTitle(): String

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(applicationContext: Context, title: String) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(drawerState = drawerState,
            drawerContent = { AppDrawer(applicationContext, scope, drawerState) }) {
            Scaffold(topBar = {
                TopAppBar(title = { Text(title) }, navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                })
            }, content = { padding ->
                ScreenContent(modifier = Modifier.padding(padding))
            })
        }
    }

    @Composable
    fun AppDrawer(
        applicationContext: Context, scope: CoroutineScope, drawerState: DrawerState
    ) {
        ModalDrawerSheet {
            Text("XPNSR", modifier = Modifier.padding(16.dp))
            HorizontalDivider()
            DrawerItem(
                "Transactions",
                TransactionsActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            DrawerItem(
                "Add Transaction",
                TransactionActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            HorizontalDivider()
            DrawerItem("Report", ReportActivity::class.java, applicationContext, scope, drawerState)
            DrawerItem(
                "Locations", MapViewActivity::class.java, applicationContext, scope, drawerState
            )
            DrawerItem(
                "Profile Photo",
                ProfilePhotoActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            DrawerItem(
                "Video Playback",
                VideoPlayActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            DrawerItem(
                "Sensor and Notifications",
                NotificationSensorActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            DrawerItem("API", ApiCallActivity::class.java, applicationContext, scope, drawerState)
            DrawerItem(
                "Animation", AnimationActivity::class.java, applicationContext, scope, drawerState
            )
        }
    }

    @Composable
    fun DrawerItem(
        text: String,
        activity: Class<*>,
        context: Context,
        scope: CoroutineScope,
        drawerState: DrawerState
    ) {
        NavigationDrawerItem(label = { Text(text = text) }, selected = false, onClick = {
            context.startActivity(
                Intent(
                    context, activity
                ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            )
            scope.launch { drawerState.close() }
        })
    }
}
