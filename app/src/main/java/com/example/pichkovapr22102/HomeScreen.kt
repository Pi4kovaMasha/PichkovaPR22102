package com.example.pichkovapr22102

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pichkovapr22102.ui.CartManager
import com.example.pichkovapr22102.ui.Product

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    val raleway = FontFamily(Font(R.font.raleway, FontWeight.Normal))
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2B2B2B)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "Главная",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF2B2B2B)
                        )
                    },
                    selected = true,
                    onClick = { Toast.makeText(context, "Главная", Toast.LENGTH_SHORT).show() }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = "Корзина",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF2B2B2B)
                        )
                    },
                    selected = false,
                    onClick = { navController.navigate("my_cart") }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "Уведомления",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF2B2B2B)
                        )
                    },
                    selected = false,
                    onClick = { Toast.makeText(context, "Уведомления", Toast.LENGTH_SHORT).show() }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "Профиль",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF2B2B2B)
                        )
                    },
                    selected = false,
                    onClick = { Toast.makeText(context, "Профиль", Toast.LENGTH_SHORT).show() }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F7F9))
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hamburger),
                    contentDescription = "Меню",
                    tint = Color(0xFF2B2B2B),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { Toast.makeText(context, "Меню", Toast.LENGTH_SHORT).show() }
                )
                Text(
                    text = "Главная",
                    fontFamily = newPeninimMT,
                    fontSize = 32.sp,
                    color = Color(0xFF2B2B2B)
                )
                Box {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Корзина",
                        tint = Color(0xFF2B2B2B),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.navigate("my_cart") }
                    )
                    Box(
                        modifier = Modifier
                            .offset(x = 10.dp, y = (-5).dp)
                            .size(8.dp)
                            .background(Color(0xFFF87265), CircleShape)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Поиск",
                            tint = Color(0xFF6A6A6A),
                            modifier = Modifier.clickable { navController.navigate("search/$searchQuery") }
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .background(Color.White, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color(0xFF48B2E7), RoundedCornerShape(14.dp))
                        .clickable { Toast.makeText(context, "Фильтры", Toast.LENGTH_SHORT).show() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sliders),
                        contentDescription = "Фильтры",
                        tint = Color(0xFFF7F7F9),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Category",
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B)
                )
                Text(
                    text = "Все",
                    fontFamily = newPeninimMT,
                    fontSize = 12.sp,
                    color = Color(0xFF48B2E7),
                    modifier = Modifier.clickable { Toast.makeText(context, "Все категории", Toast.LENGTH_SHORT).show() }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All Shoes", "Outdoor", "Tennis", "Running").forEach { category ->
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { Toast.makeText(context, category, Toast.LENGTH_SHORT).show() }
                    ) {
                        Text(
                            text = category,
                            fontFamily = newPeninimMT,
                            fontSize = 12.sp,
                            color = Color(0xFF2B2B2B),
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Популярное",
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B)
                )
                Text(
                    text = "Все",
                    fontFamily = newPeninimMT,
                    fontSize = 12.sp,
                    color = Color(0xFF48B2E7),
                    modifier = Modifier.clickable { navController.navigate("popular_products") }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 8.dp, end = 20.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                val sampleProducts = listOf(
                    Product(id = "1", name = "Nike Air Max", price = 752.00, isBestSeller = true),
                    Product(id = "2", name = "Nike Club Max", price = 584.95, isBestSeller = false)
                )
                sampleProducts.forEach { product ->
                    ProductCard(
                        product = product,
                        onAddToCartClick = {
                            CartManager.addToCart(product)
                            Toast.makeText(context, "Добавлено в корзину: ${product.name}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp)
            ) {
                Text(
                    text = "Акции",
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    color = Color(0xFF2B2B2B),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.stock),
                    contentDescription = "Акции",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(95.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onAddToCartClick: () -> Unit) {
    val context = LocalContext.current
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    Box(
        modifier = Modifier
            .size(width = 160.dp, height = 182.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(9.dp)
                .size(28.dp)
                .background(Color(0xFFF7F7F9), CircleShape)
                .clickable { Toast.makeText(context, "Добавлено в избранное", Toast.LENGTH_SHORT).show() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "Избранное",
                tint = Color(0xFF2B2B2B),
                modifier = Modifier.size(16.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 18.dp, start = 9.dp, end = 9.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.nike_shoe),
                contentDescription = product.name,
                modifier = Modifier
                    .size(117.91.dp, 70.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Column {
                if (product.isBestSeller) {
                    Text(
                        text = "Best Seller".uppercase(),
                        fontFamily = newPeninimMT,
                        fontSize = 12.sp,
                        color = Color(0xFF48B2E7)
                    )
                }
                Text(
                    text = product.name,
                    fontFamily = newPeninimMT,
                    fontSize = 16.sp,
                    color = Color(0xFF6A6A6A)
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 9.dp, bottom = 8.dp)
        ) {
            Text(
                text = "₽${product.price}",
                fontFamily = newPeninimMT,
                fontSize = 14.sp,
                color = Color(0xFF2B2B2B)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(34.dp)
                .background(Color(0xFF48B2E7), RoundedCornerShape(topStart = 16.dp))
                .clickable { onAddToCartClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
        }
    }
}