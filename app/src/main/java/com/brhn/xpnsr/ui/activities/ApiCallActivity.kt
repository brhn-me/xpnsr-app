package com.brhn.xpnsr.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.brhn.xpnsr.ui.components.ApiCallScreen

class ApiCallActivity : ComponentActivity() {
    // dummy api url
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiCallScreen()
        }
    }
}
