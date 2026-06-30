    package com.example.livemotion.ui.screens

    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.navigation.NavController
    import com.example.livemotion.ui.components.BottomNavBar

    @Composable
    fun HomeScreen(
        navController: NavController
    ) {

        Scaffold(
            bottomBar = {
                BottomNavBar(
                    currentRoute = "home",
                    navController = navController
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier.padding(padding)
            ) {
                HomeContent(
                    navController = navController
                )
            }
        }
    }