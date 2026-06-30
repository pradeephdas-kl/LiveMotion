package com.example.livemotion.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val wallpapersCollection = firestore.collection("wallpapers")

    suspend fun getWallpapers(): List<Wallpaper> {
        return try {
            wallpapersCollection
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(Wallpaper::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFeaturedWallpapers(): List<Wallpaper> {
        return try {
            wallpapersCollection
                .whereEqualTo("isFeatured", true)
                .get()
                .await()
                .toObjects(Wallpaper::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getWallpapersByCategory(category: String): List<Wallpaper> {
        return try {
            wallpapersCollection
                .whereEqualTo("category", category)
                .get()
                .await()
                .toObjects(Wallpaper::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
