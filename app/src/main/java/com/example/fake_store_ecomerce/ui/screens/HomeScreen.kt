import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.fake_store_ecomerce.R
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.navigator.Screen
import com.example.fake_store_ecomerce.ui.components.PrimaryButton
import com.example.fake_store_ecomerce.ui.items.ProductsPagingList
import com.example.fake_store_ecomerce.ui.theme.FakeStoreEcomerceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewmodel,
    modifier: Modifier = Modifier
) {
    val products: LazyPagingItems<ProductResponse> = viewModel.productsFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF5E4D3A)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5E9DA),
                    actionIconContentColor = Color(0xFF5E4D3A)
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.Cart.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Go to Cart"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    PrimaryButton(
                        text = stringResource(R.string.categories),
                        onClick = { navController.navigate(Screen.Categories.route) },
                        containerColor = Color(0xFFD4BFA8),
                        contentColor = Color(0xFF5E4D3A)
                    )
                }
            )
        },
        containerColor = Color(0xFFF5E9DA)
    ) { paddingValues ->
        ProductsPagingList(
            products = products,
            navController = navController,
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FakeStoreEcomerceTheme {
    }
}
