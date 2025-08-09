package com.example.fake_store_ecomerce.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.ui.managers.ProductsDetailsViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    productId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ProductsDetailsViewmodel = viewModel()
) {
    val productState by viewModel.productState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productId) {
        viewModel.getProductsById(productId)
    }

    // Handle snackbar messages for errors only
    LaunchedEffect(productState) {
        if (productState is ApiResult.Error) {
            snackbarHostState.showSnackbar(
                message = (productState as ApiResult.Error).message,
                duration = SnackbarDuration.Long
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (productState) {
                is ApiResult.Loading -> {
                    LoadingContent()
                }
                is ApiResult.Success -> {
                    ProductContent(
                        product = (productState as ApiResult.Success<ProductResponse>).data,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                is ApiResult.Error -> {
                    ErrorContent(
                        error = (productState as ApiResult.Error).message,
                        onRetry = { viewModel.getProductsById(productId) },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Loading product details...")
    }
}

@Composable
private fun ProductContent(
    product: ProductResponse,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Product Image - using first image from the list
        product.images?.firstOrNull()?.let { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Title
        Text(
            text = product.title ?: "Unknown Product",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category
        product.categoryResponse?.name?.let { categoryName ->
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = categoryName,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Price - Note: Your API returns Int for price, converting to proper currency format
        product.price?.let { price ->
            Text(
                text = "${price}",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        product.description?.let { description ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Add to Cart Button
        Button(
            onClick = { /* No functionality - just UI placeholder */ }, // Remove TODO()
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add to Cart", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// Navigation Setup (in your main navigation composable)
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController

//@Composable
//fun AppNavigation(
//    navController: NavHostController = rememberNavController()
//) {
//    NavHost(
//        navController = navController,
//        startDestination = "products"
//    ) {
//        composable("products") {
//            HomeScreen(
//                onProductClick = { productId ->
//                    navController.navigate("product_details/$productId")
//                },
//                onNavigateToCategories = {
//                    navController.navigate("categories")
//                }
//            )
//        }
//
//        composable("product_details/{productId}") { backStackEntry ->
//            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0
//            ProductDetailsScreen(
//                productId = productId,
//                onNavigateBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
//
//        composable("categories") {
//            CategoriesScreen(
//                onNavigateBack = {
//                    navController.popBackStack()
//                },
//                onCategoryClick = { categoryId ->
//                    navController.navigate("category_products/$categoryId")
//                }
//            )
//        }
//    }
//}

// Example of how to navigate to product details from your product list
//@Composable
//fun ProductItem(
//    product: ProductResponse,
//    onProductClick: (Int) -> Unit,
//    onAddToCart: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { product.id?.let { onProductClick(it) } },
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            // Product image
//            product.images?.firstOrNull()?.let { imageUrl ->
//                AsyncImage(
//                    model = imageUrl,
//                    contentDescription = product.title,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(8.dp)),
//                    contentScale = ContentScale.Crop
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Product title
//            Text(
//                text = product.title ?: "Unknown Product",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            // Product price
//            Text(
//                text = "${product.price ?: 0}",
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.primary,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Add to cart button
//            Button(
//                onClick = onAddToCart,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Icon(Icons.Default.ShoppingCart, contentDescription = null)
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Add to Cart")
//            }
//        }
//    }
//}
