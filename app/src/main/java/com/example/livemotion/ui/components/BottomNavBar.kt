package com.example.livemotion.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    currentRoute: String = "home",
    navController: NavController? = null
) {

    NavigationBar(
        containerColor = Color(0xFF181818)
    ) {

        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home" && navController != null) {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                                            }
                }
            },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50)
            )
        )

        NavigationBarItem(
            selected = currentRoute == "favorites",
            onClick = {
                if (currentRoute != "favorites" && navController != null) {
                    navController.navigate("favorites") {
                        popUpTo("favorites") { inclusive = true }
                                            }
                }
            },
            icon = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Favorites") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4CAF50)
            )
        )

        NavigationBarItem(
            selected = currentRoute == "downloads",
            onClick = {},
            icon = { Icon(Icons.Default.Download, null) },
            label = { Text("Downloads") }
        )

        NavigationBarItem(
            selected = currentRoute == "settings",
            onClick = {},
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("Settings") }
        )
    }
}