package com.example.livemotion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.livemotion.R
import com.example.livemotion.ui.components.CategoryCard
import com.example.livemotion.ui.components.FeaturedBanner
import com.example.livemotion.ui.components.SearchBar
import com.example.livemotion.ui.components.WallpaperCard

@Composable
fun HomeContent(
    navController: NavController
) {

    val wallpapers = listOf(
        R.drawable.wall1,
        R.drawable.wall2,
        R.drawable.wall3,
        R.drawable.wall4,
        R.drawable.wall5,
        R.drawable.wall6
    )

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

                item { CategoryCard("🌿", "Nature") }
                item { CategoryCard("🐅", "Wildlife") }
                item { CategoryCard("🚗", "Cars") }
                item { CategoryCard("🌌", "Space") }
                item { CategoryCard("🎮", "Gaming") }
                item { CategoryCard("🌑", "AMOLED") }

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

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(1100.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                userScrollEnabled = false
            ) {

                items(wallpapers) { image ->

                    WallpaperCard(
                        image = image,
                        onClick = {
                            navController.navigate("preview/$image")
                        }
                    )

                }

            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}