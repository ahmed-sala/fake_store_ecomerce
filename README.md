# FakeStore Product Viewer

## Project Overview

This Android app fetches and displays products from the FakeStore API using Jetpack Compose. It demonstrates networking, JSON data parsing, UI composition, state management, and screen navigation.

## Features

- Fetch product list and categories from FakeStore API.
- Display product lists with thumbnails, titles, and prices.
- Navigate to product detail screen showing full product info.
- Show categories screen with clickable category cards.
- Display products filtered by category.
- Show loading indicators and error messages using Snackbar.
- Add products to cart and manage cart items.
- Splash screen on app start.

## Architecture & Libraries

- **Networking:** Retrofit with Kotlin serialization for API calls.
- **UI:** Jetpack Compose for declarative UI.
- **Navigation:** Jetpack Navigation Compose for screen routing.
- **State Management:** ViewModel and StateFlow for reactive UI.
- **Image Loading:** Coil for async image loading.
- **Paging:** LazyPagingItems for efficient list loading.
- **Database:** Cart data managed via ViewModel (Room or similar can be integrated).

## Screens Description

### 1. Splash Screen
- Shows splash image on app launch.
- Waits for 3 seconds, then navigates to Home screen.

### 2. Home Screen
- Displays paginated product list.
- Button to navigate to Categories screen.
- Floating action button to navigate to Cart.

### 3. Categories Screen
- Displays a list of categories fetched from API.
- On clicking a category, navigates to products filtered by that category.

### 4. Product Details Screen
- Shows full product details including image, title, price, description.
- Add to Cart functionality with quantity management.

### 5. Cart Screen
- Displays products added to cart.
- Ability to modify quantity or remove items.

## Key Code Snippets

### Navigation Setup

NavHost(
    navController = navController,
    startDestination = "splash"
) {
    composable("splash") { SplashScreen(navController) }
    composable("home") { HomeScreen(navController, homeViewmodel, cartViewModel) }
    composable("categories") { CategoriesScreen(navController, categoryViewmodel) }
    composable("product_details/{productId}", arguments = listOf(navArgument("productId") { type = NavType.IntType })) {
        val productId = it.arguments?.getInt("productId") ?: 0
        ProductDetailsScreen(productId, onNavigateBack = { navController.popBackStack() }, ...)
    }
    composable("cart") { CartScreen(navController, cartViewModel) }
}  
How to Run
Clone the repository.

Open the project in Android Studio.

Make sure your device/emulator has internet access.

Run the app. The splash screen appears, then navigates to home.

Explore products, categories, and cart.

Notes
Ensure you have added the splash image splash.png or splash.jpg in res/drawable.

The app handles errors with Snackbar messages.

You can extend the cart functionality with persistence using Room.


