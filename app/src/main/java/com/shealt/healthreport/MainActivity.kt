package com.shealt.healthreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shealt.healthreport.ui.navigation.AppNavigation
import com.shealt.healthreport.ui.theme.HealthReportTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthReportTheme {
                AppNavigation()
            }
        }
    }
}
