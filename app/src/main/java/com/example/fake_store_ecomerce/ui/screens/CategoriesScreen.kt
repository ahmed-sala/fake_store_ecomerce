package com.example.fake_store_ecomerce.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import com.example.fake_store.networking.ApiResult
import com.example.fake_store_ecomerce.data.models.CategoryResponse
import com.example.fake_store_ecomerce.ui.managers.CategoryViewmodel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    viewModel: CategoryViewmodel = viewModel()
) {
    val categoryState by viewModel.categoryState.collectAsState()
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
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
            when (categoryState) {
                is ApiResult.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ApiResult.Success -> {
                    val categories = (categoryState as ApiResult.Success<List<CategoryResponse>>).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            CategoryItem(
                                category = category,
                                onClick = {
                                    category.id?.let { id ->
                                        navController.navigate("products_by_category/$id")
                                    }
                                }
                            )
                        }
                    }
                }
                is ApiResult.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error occurred: ${(categoryState as ApiResult.Error).message}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.getCategories() }) {
                            Text("Retry")
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
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = category.name ?: "Unknown Category",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
