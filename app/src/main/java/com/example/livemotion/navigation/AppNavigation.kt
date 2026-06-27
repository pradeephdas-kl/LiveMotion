package com.example.livemotion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.livemotion.ui.screens.HomeScreen
import com.example.livemotion.ui.screens.WallpaperPreviewScreen

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

        composable(
            route = "preview/{image}",
            arguments = listOf(
                navArgument("image") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val image = backStackEntry.arguments?.getInt("image") ?: 0

            WallpaperPreviewScreen(
                image = image,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}