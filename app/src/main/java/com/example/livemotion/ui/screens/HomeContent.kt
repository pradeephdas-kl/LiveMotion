package com.example.livemotion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.livemotion.data.Category
import com.example.livemotion.data.FirebaseRepository
import com.example.livemotion.data.Wallpaper
import com.example.livemotion.ui.components.CategoryCard
import com.example.livemotion.ui.components.FeaturedBanner
import com.example.livemotion.ui.components.SearchBar
import com.example.livemotion.ui.components.WallpaperCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeContent(
    navController: NavController
) {

    val firebaseRepository = remember { FirebaseRepository() }
    
    val wallpapers = remember { mutableStateOf<List<Wallpaper>>(emptyList()) }
    val wallpapersLoading = remember { mutableStateOf(true) }
    val wallpapersError = remember { mutableStateOf(false) }
    
    val categories = listOf(
        Category(id = "nature", name = "Nature", icon = "🌿"),
        Category(id = "wildlife", name = "Wildlife", icon = "🐅"),
        Category(id = "cars", name = "Cars", icon = "🚗"),
        Category(id = "space", name = "Space", icon = "🌌"),
        Category(id = "gaming", name = "Gaming", icon = "🎮"),
        Category(id = "amoled", name = "AMOLED", icon = "⚫")
    )

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            // Load wallpapers from Firestore
            try {
                val fetchedWallpapers = firebaseRepository.getWallpapers()
                wallpapers.value = fetchedWallpapers
                wallpapersLoading.value = false
                wallpapersError.value = fetchedWallpapers.isEmpty()
            } catch (e: Exception) {
                wallpapersLoading.value = false
                wallpapersError.value = true
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {

        item {

            Text(
                text = "LiveMotion",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar()

            Spacer(modifier = Modifier.height(20.dp))

            FeaturedBanner()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(380.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                userScrollEnabled = false
            ) {

                items(categories) { category ->
                    CategoryCard(
                        emoji = category.icon,
                        title = category.name
                    )
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Latest Wallpapers",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {

            if (wallpapersLoading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF4CAF50)
                    )
                }
            } else if (wallpapers.value.isEmpty() || wallpapersError.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No wallpapers available",
                        color = Color(0xFFB0B0B0),
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.height(1100.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {

                    items(wallpapers.value) { wallpaper ->

                        android.util.Log.d("LiveMotion", wallpaper.imageUrl)

                        WallpaperCard(
                            imageUrl = wallpaper.imageUrl,
                            onClick = {
                                navController.navigate("preview/${wallpaper.id}")
                            }
                        )

                    }

                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}