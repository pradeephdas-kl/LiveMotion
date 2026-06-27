package com.example.livemotion.ui.screens

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livemotion.utils.WallpaperUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperPreviewScreen(
    @DrawableRes image: Int,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text("Wallpaper")
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )

                    }

                }

            )

        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                color = Color(0xCC111111),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    ActionItem(
                        icon = Icons.Default.Favorite,
                        text = "Favorite"
                    ) {
                        Toast.makeText(
                            context,
                            "Coming Soon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    ActionItem(
                        icon = Icons.Default.Download,
                        text = "Download"
                    ) {

                        val success =
                            WallpaperUtils.saveWallpaper(
                                context,
                                image
                            )

                        Toast.makeText(
                            context,
                            if (success)
                                "Wallpaper Saved"
                            else
                                "Download Failed",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    ActionItem(
                        icon = Icons.Default.Wallpaper,
                        text = "Apply"
                    ) {
                        Toast.makeText(
                            context,
                            "Coming Soon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }

        }

    }

}

@Composable
fun ActionItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp
        )

    }

}