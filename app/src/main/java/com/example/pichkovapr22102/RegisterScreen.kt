package com.example.pichkovapr22102.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pichkovapr22102.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    val raleway = FontFamily(Font(R.font.raleway, FontWeight.Medium))
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isConsentChecked by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var consentError by remember { mutableStateOf("") }

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
                .padding(top = 121.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Регистрация",
                fontFamily = newPeninimMT,
                fontSize = 32.sp,
                color = Color(0xFF2B2B2B),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Заполните свои данные или продолжите через социальные медиа",
                fontFamily = newPeninimMT,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = Color(0xFF707B81),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(315.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 245.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column {
                Text(
                    text = "Ваше имя",
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B)
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = {
                        Text(
                            text = "xxxxxxxx",
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                if (nameError.isNotEmpty()) {
                    Text(
                        text = nameError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = newPeninimMT,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            Column {
                Text(
                    text = "Email",
                    fontFamily = raleway,
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B)
                )
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

            Column {
                Text(
                    text = "Пароль",
                    fontFamily = raleway,
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "****",
                            fontFamily = newPeninimMT,
                            fontSize = 14.sp,
                            color = Color(0xFF6A6A6A).copy(alpha = 0.6f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(0xFFF7F7F9), RoundedCornerShape(14.dp)),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(id = if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_slash),
                                contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль",
                                tint = Color(0xFF6A6A6A)
                            )
                        }
                    },
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = newPeninimMT,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(Color(0xFFF7F7F9), RoundedCornerShape(6.dp))
                        .clickable { isConsentChecked = !isConsentChecked },
                    contentAlignment = Alignment.Center
                ) {
                    if (isConsentChecked) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Согласие",
                            tint = Color(0xFF2B2B2B),
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
                Text(
                    text = "Даю согласие на обработку персональных данных",
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    color = Color(0xFF6A6A6A),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                )
            }
            if (consentError.isNotEmpty()) {
                Text(
                    text = consentError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontFamily = newPeninimMT,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }
        }

        Button(
            onClick = {
                nameError = if (name.isEmpty()) "Имя не может быть пустым" else ""
                emailError = if (email.isEmpty()) "Email не может быть пустым" else if (!email.contains("@")) "Неверный формат email" else ""
                passwordError = if (password.isEmpty()) "Пароль не может быть пустым" else if (password.length < 6) "Пароль должен быть не менее 6 символов" else ""
                consentError = if (!isConsentChecked) "Необходимо дать согласие" else ""

                if (nameError.isEmpty() && emailError.isEmpty() && passwordError.isEmpty() && consentError.isEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                                navController.navigate("sign_in") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Ошибка: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .offset(y = 583.dp)
                .clip(RoundedCornerShape(13.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48B2E7))
        ) {
            Text(
                text = "Зарегистрироваться",
                fontFamily = newPeninimMT,
                fontSize = 14.sp,
                color = Color(0xFFF7F7F9)
            )
        }

        Text(
            text = "Есть аккаунт? Войти",
            fontFamily = newPeninimMT,
            fontSize = 16.sp,
            color = Color(0xFF6A6A6A),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .offset(y = 746.dp)
                .clickable { navController.navigate("sign_in") }
        )
    }
}