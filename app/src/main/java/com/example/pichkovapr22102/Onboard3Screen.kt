package com.example.pichkovapr22102.ui

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pichkovapr22102.R

@Composable
fun Onboard3Screen(navController: NavController, sharedPrefs: SharedPreferences) {
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF48B2E7),
                        Color(0xFF2B6B8B)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_3),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(302.dp)
                .align(Alignment.TopCenter)
                .offset(y = 80.dp)
        )
        Text(
            text = "У вас есть сила, чтобы",
            fontFamily = newPeninimMT,
            fontSize = 34.sp,
            lineHeight = 44.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(315.dp)
                .align(Alignment.Center)
                .offset(y = 80.5.dp)
        )
        Text(
            text = "В вашей комнате много красивых\nи привлекательных растений",
            fontFamily = newPeninimMT,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color(0xFFD8D8D8),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(322.dp)
                .align(Alignment.Center)
                .offset(y = 161.dp)
        )
        Box(
            modifier = Modifier
                .width(335.dp)
                .height(50.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-70).dp)
                .clip(RoundedCornerShape(13.dp))
                .background(Color.White)
                .clickable {
                    sharedPrefs.edit().putBoolean("hasCompletedOnboarding", true).apply()
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Далее",
                fontFamily = newPeninimMT,
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = Color(0xFF2B2B2B),
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-30).dp)
                .width(123.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(5.dp)
                    .background(Color(0xFF2B6B8B))
            )
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(5.dp)
                    .background(Color(0xFF2B6B8B))
            )
            Box(
                modifier = Modifier
                    .width(43.dp)
                    .height(5.dp)
                    .background(Color.White)
            )
        }
    }
}