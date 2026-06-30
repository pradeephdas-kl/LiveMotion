package com.example.livemotion.data

import android.content.Context

class FavoriteRepository(context: Context) {

    private val dao = AppDatabase
        .getDatabase(context)
        .favoriteDao()

    suspend fun getAll(): List<FavoriteWallpaper> {
        return dao.getAll()
    }

    suspend fun isFavorite(imageRes: Int): Boolean {
        return dao.isFavorite(imageRes)
    }

    suspend fun add(imageRes: Int) {
        dao.insert(
            FavoriteWallpaper(imageRes)
        )
    }

    suspend fun remove(imageRes: Int) {
        dao.delete(
            FavoriteWallpaper(imageRes)
        )
    }

    suspend fun toggle(imageRes: Int): Boolean {

        return if (dao.isFavorite(imageRes)) {

            dao.delete(
                FavoriteWallpaper(imageRes)
            )

            false

        } else {

            dao.insert(
                FavoriteWallpaper(imageRes)
            )

            true
        }
    }
}