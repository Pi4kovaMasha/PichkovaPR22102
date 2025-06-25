package com.example.pichkovapr22102

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pichkovapr22102.ui.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setContent {
            val navController = rememberNavController()
            val auth = FirebaseAuth.getInstance()
            val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val hasCompletedOnboarding = sharedPrefs.getBoolean("hasCompletedOnboarding", false)
            val startDestination = when {
                auth.currentUser == null -> "sign_in"
                !hasCompletedOnboarding -> "splash"
                else -> "home"
            }

            NavHost(navController = navController, startDestination = startDestination) {
                composable("sign_in") { SignInScreen(navController, sharedPrefs) }
                composable("register") { RegisterScreen(navController) }
                composable("forgot_password") { ForgotPasswordScreen(navController) }
                composable("splash") { SplashScreen(navController) }
                composable("onboard1") { Onboard1Screen(navController) }
                composable("onboard2") { Onboard2Screen(navController) }
                composable("onboard3") { Onboard3Screen(navController, sharedPrefs) }
                composable("home") { HomeScreen(navController) }
                composable("popular_products") { PopularProductsScreen(navController) }
                composable("my_cart") { MyCartScreen(navController) }
                composable("search/{query}") { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query") ?: ""
                    SearchScreen(navController, query)
                }
            }
        }
    }
}

@Composable
fun SignInScreen(navController: NavHostController, sharedPrefs: SharedPreferences) {
    val auth = try {
        FirebaseAuth.getInstance()
    } catch (e: Exception) {
        null
    }
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    val nunitoBold = FontFamily(Font(R.font.nunito_bold, FontWeight.Bold))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = 121.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Привет!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = newPeninimMT,
                color = Color(0xFF2B2B2B),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Заполните свои данные или продолжите через социальные медиа",
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = nunitoBold,
                color = Color(0xFF707B81),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(315.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 245.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column {
                Text(
                    text = "Email",
                    fontSize = 16.sp,
                    fontFamily = nunitoBold,
                    color = Color(0xFF2B2B2B)
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            "xyz@gmail.com",
                            color = Color(0xFF6A6A6A).copy(alpha = 0.6f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(0xFFF7F7F9), RoundedCornerShape(15.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF7F7F9),
                        unfocusedContainerColor = Color(0xFFF7F7F9),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp,
                        fontFamily = nunitoBold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = nunitoBold,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

            Column {
                Text(
                    text = "Пароль",
                    fontSize = 16.sp,
                    fontFamily = nunitoBold,
                    color = Color(0xFF2B2B2B)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("****", color = Color(0xFF6A6A6A).copy(alpha = 0.6f)) },
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
                        fontFamily = nunitoBold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (passwordError.isNotEmpty()) {
                    Text(
                        text = passwordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = nunitoBold,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
                Text(
                    text = "Восстановить",
                    fontSize = 12.sp,
                    fontFamily = nunitoBold,
                    color = Color(0xFF707B81),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .clickable { navController.navigate("forgot_password") }
                )
            }

            Button(
                onClick = {
                    emailError =
                        if (email.isEmpty()) "Email не может быть пустым" else if (!email.contains("@")) "Неверный формат email" else ""
                    passwordError =
                        if (password.isEmpty()) "Пароль не может быть пустым" else if (password.length < 6) "Пароль должен быть не менее 6 символов" else ""
                    if (emailError.isEmpty() && passwordError.isEmpty()) {
                        if (auth != null) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Успешный вход!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        if (!sharedPrefs.getBoolean(
                                                "hasCompletedOnboarding",
                                                false
                                            )
                                        ) {
                                            sharedPrefs.edit()
                                                .putBoolean("hasCompletedOnboarding", false).apply()
                                        }
                                        navController.navigate("splash") {
                                            popUpTo("sign_in") { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Ошибка: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                context,
                                "Firebase не инициализирован",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(14.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48B2E7))
            ) {
                Text(
                    text = "Войти",
                    fontSize = 14.sp,
                    fontFamily = nunitoBold,
                    color = Color(0xFFF7F7F9)
                )
            }
        }

        Text(
            text = "Вы впервые? Создать пользователя",
            fontSize = 16.sp,
            fontFamily = nunitoBold,
            color = Color(0xFF6A6A6A),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
                .clickable { navController.navigate("register") }
        )
    }
}