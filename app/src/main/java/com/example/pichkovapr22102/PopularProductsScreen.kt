package com.example.pichkovapr22102.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pichkovapr22102.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val isBestSeller: Boolean = false
)

@Composable
fun PopularProductsScreen(navController: NavController) {
    val context = LocalContext.current
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    val firestore = FirebaseFirestore.getInstance()
    val productsFlow = MutableStateFlow<List<Product>>(emptyList())

    // Загрузка данных из Firestore
    firestore.collection("products")
        .get()
        .addOnSuccessListener { result ->
            val products = result.documents.mapNotNull { doc ->
                try {
                    Product(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        price = doc.getDouble("price") ?: 0.0,
                        isBestSeller = doc.getBoolean("isBestSeller") ?: false
                    )
                } catch (e: Exception) {
                    null
                }
            }
            productsFlow.value = products
        }
        .addOnFailureListener {
            Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
        }

    val products by productsFlow.collectAsState()

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
                text = "Популярное",
                fontFamily = newPeninimMT,
                fontSize = 16.sp,
                color = Color(0xFF2B2B2B)
            )
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape)
                    .clickable { Toast.makeText(context, "Избранное", Toast.LENGTH_SHORT).show() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "Избранное",
                    tint = Color(0xFF2B2B2B),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                ProductCard(
                    product = product,
                    onFavoriteClick = { Toast.makeText(context, "Добавлено в избранное: ${product.name}", Toast.LENGTH_SHORT).show() },
                    onAddToCartClick = {
                        CartManager.addToCart(product)
                        Toast.makeText(context, "Добавлено в корзину: ${product.name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onFavoriteClick: () -> Unit, onAddToCartClick: () -> Unit) {
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
                .clip(CircleShape)
                .clickable { onFavoriteClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "Избранное",
                tint = Color(0xFFF87265),
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