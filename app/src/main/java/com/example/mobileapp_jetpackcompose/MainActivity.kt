package com.example.mobileapp_jetpack


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mobileapp_jetpack.navigation.AppNavGraph
import com.example.mobileapp_jetpack.ui.theme.MobileAppJetpackTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppJetpackTheme {
                Surface(modifier = Modifier) {
                    AppNavGraph()
                }
            }
        }
    }
}
