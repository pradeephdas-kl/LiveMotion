package com.example.livemotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.livemotion.ui.screens.HomeScreen
import com.example.livemotion.ui.theme.LiveMotionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiveMotionTheme {
                HomeScreen()
            }
        }
    }
}