package com.example.livemotion.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.livemotion.data.FirebaseRepository
import com.example.livemotion.data.Wallpaper
import com.example.livemotion.utils.WallpaperManagerUtil
import com.example.livemotion.utils.WallpaperUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperPreviewScreen(
    wallpaperId: String,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val firebaseRepository = remember { FirebaseRepository() }
    val wallpaper = remember { mutableStateOf<Wallpaper?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val isFavorite = remember { mutableStateOf(false) }
    val showApplySheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(wallpaperId) {
        withContext(Dispatchers.IO) {
            try {
                val wallpapers = firebaseRepository.getWallpapers()
                val found = wallpapers.find { it.id == wallpaperId }
                if (found != null) {
                    wallpaper.value = found
                    isFavorite.value = false
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    if (showApplySheet.value && wallpaper.value != null) {
        ModalBottomSheet(
            onDismissRequest = { showApplySheet.value = false },
            sheetState = sheetState
        ) {
            ApplyWallpaperBottomSheet(
                imageUrl = wallpaper.value!!.imageUrl,
                onClose = { showApplySheet.value = false },
                context = context
            )
        }
    }

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

            if (isLoading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF4CAF50)
                    )
                }
            } else if (wallpaper.value != null) {
                AsyncImage(
                    model = wallpaper.value!!.imageUrl,
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
                            icon = if (isFavorite.value) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            text = "Favorite",
                            isFilled = isFavorite.value
                        ) {
                            coroutineScope.launch {
                                isFavorite.value = !isFavorite.value
                            }
                        }

                        ActionItem(
                            icon = Icons.Default.Download,
                            text = "Download"
                        ) {

                            wallpaper.value?.imageUrl?.let { url ->

                                coroutineScope.launch {

                                    val success = withContext(Dispatchers.IO) {
                                        WallpaperUtils.saveWallpaperFromUrl(
                                            context,
                                            url
                                        )
                                    }

                                    Toast.makeText(
                                        context,
                                        if (success)
                                            "Wallpaper Saved"
                                        else
                                            "Download Failed",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }

                        }

                        ActionItem(
                            icon = Icons.Default.Wallpaper,
                            text = "Apply"
                        ) {
                            showApplySheet.value = true
                        }

                    }

                }
            }

        }

    }

}

@Composable
fun ApplyWallpaperBottomSheet(
    imageUrl: String,
    onClose: () -> Unit,
    context: android.content.Context
) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Apply Wallpaper",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ApplyOption(
            emoji = "🏠",
            title = "Home Screen"
        ) {

            coroutineScope.launch(Dispatchers.IO) {

                WallpaperManagerUtil.setHomeWallpaperFromUrl(
                    context,
                    imageUrl
                )

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        context,
                        "Home wallpaper applied",
                        Toast.LENGTH_SHORT
                    ).show()

                    onClose()
                }
            }

        }

        ApplyOption(
            emoji = "🔒",
            title = "Lock Screen"
        ) {

            coroutineScope.launch(Dispatchers.IO) {

                WallpaperManagerUtil.setLockWallpaperFromUrl(
                    context,
                    imageUrl
                )

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        context,
                        "Lock wallpaper applied",
                        Toast.LENGTH_SHORT
                    ).show()

                    onClose()
                }
            }

        }

        ApplyOption(
            emoji = "📱",
            title = "Home & Lock Screen"
        ) {

            coroutineScope.launch(Dispatchers.IO) {

                WallpaperManagerUtil.setBothWallpapersFromUrl(
                    context,
                    imageUrl
                )

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        context,
                        "Wallpaper applied",
                        Toast.LENGTH_SHORT
                    ).show()

                    onClose()
                }
            }

        }

        ApplyOption(
            emoji = "❌",
            title = "Cancel",
            isDanger = true
        ) {
            onClose()
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ApplyOption(
    emoji: String,
    title: String,
    isDanger: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = if (isDanger) Color(0x1AFF1744) else Color(0x1A4CAF50),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = emoji,
                fontSize = 28.sp
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (isDanger) Color(0xFFFF1744) else Color.White
            )
        }
    }
}

@Composable
fun ActionItem(
    icon: ImageVector,
    text: String,
    isFilled: Boolean = false,
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
            tint = if (isFilled) Color(0xFFFF1744) else Color.White,
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