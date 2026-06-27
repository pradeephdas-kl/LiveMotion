package com.example.livemotion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.livemotion.ui.components.SearchBar
import com.example.livemotion.ui.components.FeaturedBanner
import com.example.livemotion.ui.components.CategoryCard
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import com.example.livemotion.ui.components.WallpaperCard

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

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
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                CategoryCard("🌿", "Nature")
            }

            item {
                CategoryCard("🐅", "Wildlife")
            }

            item {
                CategoryCard("🚗", "Cars")
            }

            item {
                CategoryCard("🌌", "Space")
            }

            item {
                CategoryCard("🎮", "Gaming")
            }

            item {
                CategoryCard("🌑", "AMOLED")
            }

        }
    }
}