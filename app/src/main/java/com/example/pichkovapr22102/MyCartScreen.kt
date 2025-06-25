package com.example.pichkovapr22102.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.shadow
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Модель для элемента корзины
data class CartItem(
    val product: Product,
    val quantity: Int
)

// Управление корзиной
object CartManager {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.product.id == product.id }
            if (existingItem != null) {
                currentItems.map { item ->
                    if (item.product.id == product.id) item.copy(quantity = item.quantity + 1)
                    else item
                }
            } else {
                currentItems + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { currentItems ->
            currentItems.filter { it.product.id != productId }
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(productId)
        } else {
            _cartItems.update { currentItems ->
                currentItems.map { item ->
                    if (item.product.id == productId) item.copy(quantity = newQuantity)
                    else item
                }
            }
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity } + 60.20 // Добавляем стоимость доставки
    }

    fun getSubtotalPrice(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getItemCount(): Int {
        return _cartItems.value.sumOf { it.quantity }
    }
}

@Composable
fun MyCartScreen(navController: NavController) {
    val context = LocalContext.current
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    val raleway = FontFamily(Font(R.font.raleway, FontWeight.Medium))
    val cartItems by CartManager.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F9))
            .padding(top = 16.dp)
    ) {
        // Верхняя панель
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
                text = "Корзина",
                fontFamily = newPeninimMT,
                fontSize = 16.sp,
                color = Color(0xFF2B2B2B)
            )
            Spacer(modifier = Modifier.size(44.dp)) // Балансировка для симметрии
        }

        // Количество товаров
        Text(
            text = "${CartManager.getItemCount()} товара",
            fontFamily = newPeninimMT,
            fontSize = 16.sp,
            color = Color(0xFF2B2B2B),
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp)
        )

        // Список товаров
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItems.size) { index ->
                val item = cartItems[index]
                CartItemCard(
                    cartItem = item,
                    onRemoveClick = { CartManager.removeFromCart(item.product.id) },
                    onQuantityChange = { newQuantity ->
                        CartManager.updateQuantity(item.product.id, newQuantity)
                    }
                )
            }
        }

        // Итоговая секция
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Сумма
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Сумма",
                        fontFamily = newPeninimMT,
                        fontSize = 16.sp,
                        color = Color(0xFF707B81)
                    )
                    Text(
                        text = "₽${String.format("%.2f", CartManager.getSubtotalPrice())}",
                        fontFamily = newPeninimMT,
                        fontSize = 16.sp,
                        color = Color(0xFF2B2B2B)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Доставка
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Доставка",
                        fontFamily = newPeninimMT,
                        fontSize = 16.sp,
                        color = Color(0xFF707B81)
                    )
                    Text(
                        text = "₽60.20",
                        fontFamily = newPeninimMT,
                        fontSize = 16.sp,
                        color = Color(0xFF2B2B2B)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Разделительная линия
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(0xFF707B81), shape = RoundedCornerShape(1.dp))
                        .clip(RoundedCornerShape(1.dp))
                ) {
                    // Пунктирная линия (имитация dashed)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                color = Color(0xFF707B81).copy(alpha = 0.5f),
                                shape = RoundedCornerShape(1.dp)
                            )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Итого
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Итого",
                        fontFamily = newPeninimMT,
                        fontSize = 16.sp,
                        color = Color(0xFF2B2B2B)
                    )
                    Text(
                        text = "₽${String.format("%.2f", CartManager.getTotalPrice())}",
                        fontFamily = newPeninimMT,
                        fontSize = 16.sp,
                        color = Color(0xFF48B2E7)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                // Кнопка "Оформить заказ"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color(0xFF48B2E7), RoundedCornerShape(12.dp))
                        .clickable { Toast.makeText(context, "Оформить заказ", Toast.LENGTH_SHORT).show() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Оформить заказ",
                        fontFamily = raleway,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onRemoveClick: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    val newPeninimMT = FontFamily(Font(R.font.new_peninim_mt, FontWeight.Normal))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        // Количество
        Box(
            modifier = Modifier
                .width(58.dp)
                .fillMaxHeight()
                .background(Color(0xFF48ABE7), RoundedCornerShape(8.dp))
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clickable { onQuantityChange(cartItem.quantity + 1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }
                Text(
                    text = cartItem.quantity.toString(),
                    fontFamily = newPeninimMT,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clickable { onQuantityChange(cartItem.quantity - 1) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "−",
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
        // Изображение
        Box(
            modifier = Modifier
                .offset(x = 68.dp)
                .size(87.dp, 85.dp)
                .background(Color(0xFFF7F7F9), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nike_shoe),
                contentDescription = cartItem.product.name,
                modifier = Modifier.size(86.dp, 55.dp)
            )
        }
        // Название и цена
        Column(
            modifier = Modifier
                .offset(x = 165.dp, y = 20.dp)
                .width(188.dp)
        ) {
            Text(
                text = cartItem.product.name,
                fontFamily = newPeninimMT,
                fontSize = 16.sp,
                color = Color(0xFF2B2B2B),
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "₽${String.format("%.2f", cartItem.product.price)}",
                fontFamily = newPeninimMT,
                fontSize = 14.sp,
                color = Color(0xFF2B2B2B),
                lineHeight = 14.sp
            )
        }
        // Кнопка удаления
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(58.dp, 104.dp)
                .background(Color(0xFFF87265), RoundedCornerShape(8.dp))
                .clickable { onRemoveClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Удалить",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}