package com.example.pichkovapr22102.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pichkovapr22102.R
import com.google.gson.Gson

@Composable
fun SearchScreen(navController: NavController, initialQuery: String = "") {
    val context = LocalContext.current
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    var searchQuery by remember { mutableStateOf(TextFieldValue(initialQuery)) }
    val searchHistory = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        val historyJson = sharedPrefs.getString("search_history", null)
        if (historyJson != null) {
            val type = object : com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken<List<String>>() {}.type
            val savedHistory: List<String> = Gson().fromJson(historyJson, type)
            searchHistory.addAll(savedHistory)
        }
    }

    fun saveSearchQuery(query: String) {
        if (query.isNotBlank() && !searchHistory.contains(query)) {
            searchHistory.add(0, query)
            if (searchHistory.size > 10) searchHistory.removeAt(searchHistory.size - 1)
            val historyJson = Gson().toJson(searchHistory)
            sharedPrefs.edit().putString("search_history", historyJson).apply()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F9))
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape)
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
            Text(
                text = "Поиск",
                fontFamily = newPeninimMT,
                fontSize = 16.sp,
                color = Color(0xFF2B2B2B)
            )
            Spacer(modifier = Modifier.size(44.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .height(52.dp)
                .background(Color.White, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Поиск",
                tint = Color(0xFF6A6A6A),
                modifier = Modifier.size(24.dp)
            )
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "Поиск",
                        fontFamily = newPeninimMT,
                        fontSize = 12.sp,
                        color = Color(0xFF6A6A6A)
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    fontFamily = newPeninimMT,
                    color = Color(0xFF6A6A6A)
                ),
                keyboardOptions = KeyboardOptions.Default,
                singleLine = true,
                trailingIcon = {
                    if (searchQuery.text.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_microphone),
                            contentDescription = "голосовой ввод",
                            tint = Color(0xFF707B81),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { searchQuery = TextFieldValue("") }
                        )
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(searchHistory.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(22.dp)
                        .clickable {
                            searchQuery = TextFieldValue(searchHistory[index])
                            saveSearchQuery(searchHistory[index])
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = "История",
                        tint = Color(0xFF707B81),
                        modifier = Modifier.size(16.5.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = searchHistory[index],
                        fontFamily = newPeninimMT,
                        fontSize = 14.sp,
                        color = Color(0xFF2B2B2B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                if (searchQuery.text.isNotBlank()) {
                    saveSearchQuery(searchQuery.text)
                    Toast.makeText(context, "Поиск: ${searchQuery.text}", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .height(43.dp)
                .clip(RoundedCornerShape(5.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48B2E7))
        ) {
            Text(
                text = "Поиск",
                fontFamily = FontFamily(Font(R.font.raleway, FontWeight.Normal)),
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}