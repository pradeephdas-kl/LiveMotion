package com.example.livemotion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livemotion.data.FavoriteRepository
import com.example.livemotion.data.FirebaseRepository
import com.example.livemotion.data.Wallpaper
import com.example.livemotion.ui.components.BottomNavBar
import com.example.livemotion.ui.components.WallpaperCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FavoritesScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val favoriteRepository = remember { FavoriteRepository(context) }
    val firebaseRepository = remember { FirebaseRepository() }

    val favorites = remember { mutableStateOf<List<Wallpaper>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    val navBackStackEntry = navController.currentBackStackEntry

    LaunchedEffect(navBackStackEntry) {

        withContext(Dispatchers.IO) {

            val favoriteIds =
                favoriteRepository.getAll().map { it.wallpaperId }

            val allWallpapers =
                firebaseRepository.getWallpapers()

            favorites.value =
                allWallpapers.filter {
                    favoriteIds.contains(it.id)
                }

            isLoading.value = false
        }
    }

    Scaffold(

        bottomBar = {

            BottomNavBar(
                currentRoute = "favorites",
                navController = navController
            )

        }

    ) { padding ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(padding)

        ) {

            when {

                isLoading.value -> {

                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF4CAF50)
                    )

                }

                favorites.value.isEmpty() -> {

                    EmptyFavoritesState()

                }

                else -> {

                    LazyVerticalGrid(

                        columns = GridCells.Fixed(2),

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),

                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)

                    ) {

                        items(favorites.value) { wallpaper ->

                            WallpaperCard(

                                imageUrl = wallpaper.imageUrl,

                                onClick = {

                                    navController.navigate(
                                        "preview/${wallpaper.id}"
                                    )

                                }

                            )

                        }

                    }

                }

            }

        }

    }

}

@Composable
private fun EmptyFavoritesState() {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Icon(

            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color(0xFFFF1744),
            modifier = Modifier.height(100.dp)

        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(

            text = "No Favorites Yet",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "Tap the heart icon on any wallpaper.",
            color = Color.Gray,
            fontSize = 14.sp

        )

    }

}