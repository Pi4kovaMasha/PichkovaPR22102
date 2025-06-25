package com.example.pichkovapr22102.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pichkovapr22102.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, top = 66.dp)
                .size(44.dp)
                .background(Color(0xFFF7F7F9), CircleShape)
                .clickable { navController.popBackStack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "Назад",
                tint = Color(0xFF2B2B2B),
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 130.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Забыл пароль",
                    fontFamily = newPeninimMT,
                    fontSize = 32.sp,
                    color = Color(0xFF2B2B2B),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Введите свою учетную запись для сброса",
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFF707B81),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(335.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "xyz@gmail.com",
                            fontFamily = newPeninimMT,
                            fontSize = 14.sp,
                            color = Color(0xFF6A6A6A).copy(alpha = 0.6f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(0xFFF7F7F9), RoundedCornerShape(14.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF7F7F9),
                        unfocusedContainerColor = Color(0xFFF7F7F9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        fontFamily = newPeninimMT
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = newPeninimMT,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                emailError = if (email.isEmpty()) "Email не может быть пустым" else if (!email.contains("@")) "Неверный формат email" else ""
                if (emailError.isEmpty()) {
                    auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { fetchTask ->
                        if (fetchTask.isSuccessful) {
                            val signInMethods = fetchTask.result?.signInMethods ?: emptyList()
                            if (signInMethods.isEmpty()) {
                                Toast.makeText(context, "Пользователь с таким email не найден", Toast.LENGTH_SHORT).show()
                            } else {
                                auth.sendPasswordResetEmail(email).addOnCompleteListener { resetTask ->
                                    if (resetTask.isSuccessful) {
                                        Toast.makeText(context, "Письмо для сброса пароля отправлено на $email", Toast.LENGTH_SHORT).show()
                                        navController.navigate("sign_in") {
                                            popUpTo("forgot_password") { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(context, "Ошибка отправки письма: ${resetTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(context, "Ошибка проверки email: ${fetchTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp)
                .offset(y = 300.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48B2E7)),
            shape = RoundedCornerShape(13.dp)
        ) {
            Text(
                text = "Отправить",
                fontFamily = newPeninimMT,
                fontSize = 14.sp,
                color = Color(0xFFF7F7F9)
            )
        }
    }
}