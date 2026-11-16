package com.example.nexthire

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.nexthire.ui.screens.HomeScreen
import com.example.nexthire.ui.screens.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun NextHireApp() {
    var showSplash by rememberSaveable { mutableStateOf(true) }

    if (showSplash) {
        LaunchedEffect(Unit) {
            delay(1_500L)
            showSplash = false
        }

        SplashScreen()
    } else {
        HomeScreen()
    }
}