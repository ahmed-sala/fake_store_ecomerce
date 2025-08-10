package com.example.fake_store_ecomerce.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.CategoryResponse
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    viewModel: CategoryViewmodel = viewModel()
) {
    val categoryState by viewModel.categoryState.collectAsState()
    val productsState by viewModel.productsState.collectAsState()

    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    LaunchedEffect(categoryState) {
        if (categoryState is ApiResult.Error) {
            snackbarHostState.showSnackbar(
                message = (categoryState as ApiResult.Error).message,
                duration = SnackbarDuration.Long
            )
        }
    }

    LaunchedEffect(productsState) {
        if (productsState is ApiResult.Error) {
            snackbarHostState.showSnackbar(
                message = (productsState as ApiResult.Error).message,
                duration = SnackbarDuration.Long
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories & Products") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (categoryState) {
                is ApiResult.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
                is ApiResult.Success -> {
                    val categories = (categoryState as ApiResult.Success<List<CategoryResponse>>).data
                    categories.forEach { category ->
                        item {
                            CategoryItem(
                                category = category,
                                isSelected = selectedCategoryId == category.id,
                                onClick = {
                                    if (selectedCategoryId == category.id) {
                                        selectedCategoryId = null
                                    } else {
                                        selectedCategoryId = category.id
                                        viewModel.getProductsByCategoryId(category.id ?: 0)
                                    }
                                }
                            )
                        }
                        if (selectedCategoryId == category.id) {
                            when (productsState) {
                                is ApiResult.Loading -> {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentWidth(Alignment.CenterHorizontally)
                                                .padding(8.dp)
                                        )
                                    }
                                }
                                is ApiResult.Success -> {
                                    val products = (productsState as ApiResult.Success<List<ProductResponse>>).data
                                    if (products.isEmpty()) {
                                        item {
                                            Text(
                                                "No products found",
                                                modifier = Modifier.padding(8.dp),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    } else {

                                        item {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                                            ) {
                                                products.forEach { product ->
                                                    ProductItem(
                                                        product = product,
                                                        onClick = {
                                                            navController.navigate("product_details/${product.id ?: 0}")
                                                        }
                                                    )
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                                is ApiResult.Error -> {
                                    item {
                                        Text(
                                            text = "Error loading products",
                                            color = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.padding(8.dp),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is ApiResult.Error -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: ${(categoryState as ApiResult.Error).message}",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = { viewModel.getCategories() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: CategoryResponse,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 12.dp else 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = category.image ?: "",
                contentDescription = category.name,
                modifier = Modifier
                    .size(72.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = category.name ?: "Unknown Category",
                style = MaterialTheme.typography.titleMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun ProductItem(
    product: ProductResponse,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.images?.firstOrNull() ?: "",
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = product.title ?: "Unknown Product",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
