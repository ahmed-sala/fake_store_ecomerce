package com.example.fake_store_ecomerce.ui.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.fake_store_ecomerce.db.CartViewModel
import com.example.fake_store_ecomerce.navigator.Screen

@Composable
fun CartFloatingActionButton(
    cartViewModel: CartViewModel,
    navController: NavController
) {
    val totalItems by cartViewModel.totalItemCount.collectAsState()
    var showCartDialog by remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { showCartDialog = true }
    ) {
        BadgedBox(
            badge = {
                if (totalItems > 0) {
                    Badge {
                        Text(
                            text = if (totalItems > 99) "99+" else totalItems.toString(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
        }
    }

    if (showCartDialog) {
        CartDialog(
            cartViewModel = cartViewModel,
            onDismiss = { showCartDialog = false },
            onGoToCart = {
                showCartDialog = false
                navController.navigate(Screen.Cart.route)
            }
        )
    }
}