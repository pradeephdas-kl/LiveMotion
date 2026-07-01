package com.example.livemotion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.livemotion.ui.screens.FavoritesScreen
import com.example.livemotion.ui.screens.HomeScreen
import com.example.livemotion.ui.screens.WallpaperCropScreen
import com.example.livemotion.ui.screens.WallpaperPreviewScreen
import java.net.URLDecoder

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                navController = navController
            )
        }

        composable("favorites") {
            FavoritesScreen(
                navController = navController
            )
        }

        composable(
            route = "preview/{wallpaperId}",
            arguments = listOf(
                navArgument("wallpaperId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val wallpaperId =
                backStackEntry.arguments?.getString("wallpaperId") ?: ""

            WallpaperPreviewScreen(
                wallpaperId = wallpaperId,
                navController = navController,
                onBack = {
                    navController.popBackStack()
                }
            )

        }

        composable(
            route = "crop/{imageUrl}",
            arguments = listOf(
                navArgument("imageUrl") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val imageUrl = URLDecoder.decode(
                backStackEntry.arguments?.getString("imageUrl") ?: "",
                "UTF-8"
            )

            WallpaperCropScreen(
                imageUrl = imageUrl,
                onBack = {
                    navController.popBackStack()
                },
                onApply = {

                }
            )

        }

    }

}