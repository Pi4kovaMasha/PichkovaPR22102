package com.example.pichkovapr22102.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pichkovapr22102.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboard1") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF48B2E7)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(129.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.bag_2),
            contentDescription = "Logo",
            modifier = Modifier.size(60.dp)
        )
    }
}