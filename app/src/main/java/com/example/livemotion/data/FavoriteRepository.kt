package com.example.livemotion.data

import android.content.Context

class FavoriteRepository(context: Context) {

    private val dao = AppDatabase
        .getDatabase(context)
        .favoriteDao()

    suspend fun getAll(): List<FavoriteWallpaper> {
        return dao.getAll()
    }

    suspend fun isFavorite(wallpaperId: String): Boolean {
        return dao.isFavorite(wallpaperId)
    }

    suspend fun add(wallpaperId: String) {
        dao.insert(
            FavoriteWallpaper(wallpaperId)
        )
    }

    suspend fun remove(wallpaperId: String) {
        dao.delete(
            FavoriteWallpaper(wallpaperId)
        )
    }

    suspend fun toggle(wallpaperId: String): Boolean {

        return if (dao.isFavorite(wallpaperId)) {

            dao.delete(
                FavoriteWallpaper(wallpaperId)
            )

            false

        } else {

            dao.insert(
                FavoriteWallpaper(wallpaperId)
            )

            true
        }
    }
}