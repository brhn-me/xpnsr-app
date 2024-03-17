package com.brhn.xpnsr.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
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
            }, floatingActionButton = FabContent() ?: {}, content = { padding ->
                ScreenContent(modifier = Modifier.padding(padding))
            })
        }
    }

    @Composable
    open fun FabContent(): @Composable (() -> Unit)? = null

    @Composable
    fun AppDrawer(
        applicationContext: Context, scope: CoroutineScope, drawerState: DrawerState
    ) {
        ModalDrawerSheet {
            Text(
                text = "XPNSR",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
            HorizontalDivider()
            DrawerItem(
                "Transactions",
                Icons.Filled.List,
                TransactionsActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )

            DrawerItem(
                "Report",
                Icons.Filled.MailOutline,
                ReportActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )

            DrawerItem(
                "Locations",
                Icons.Filled.Place,
                LocationsActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )

            HorizontalDivider()


            DrawerItem(
                "Profile Photo",
                Icons.Filled.AccountCircle,
                ProfilePhotoActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            DrawerItem(
                "Video Playback",
                Icons.Filled.PlayArrow,
                VideoPlayBackActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
            DrawerItem(
                "API Calls",
                Icons.Filled.MailOutline,
                ApiCallActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )

            DrawerItem(
                "Animation",
                Icons.Filled.Face,
                AnimationActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )

            DrawerItem(
                "Sensor and Notifications",
                Icons.Filled.Notifications,
                NotificationSensorActivity::class.java,
                applicationContext,
                scope,
                drawerState
            )
        }
    }

    @Composable
    fun DrawerItem(
        text: String,
        icon: ImageVector,
        activity: Class<*>,
        context: Context,
        scope: CoroutineScope,
        drawerState: DrawerState
    ) {
        NavigationDrawerItem(label = { Text(text = text) },
            icon = { Icon(imageVector = icon, contentDescription = null) },
            selected = false,
            onClick = {
                context.startActivity(
                    Intent(
                        context, activity
                    ).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
                scope.launch { drawerState.close() }
            })
    }
}
