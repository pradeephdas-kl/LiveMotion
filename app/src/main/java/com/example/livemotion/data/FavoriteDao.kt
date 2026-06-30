package com.example.livemotion.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_wallpapers")
    suspend fun getAll(): List<FavoriteWallpaper>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_wallpapers WHERE imageRes = :imageRes)")
    suspend fun isFavorite(imageRes: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteWallpaper: FavoriteWallpaper)

    @Delete
    suspend fun delete(favoriteWallpaper: FavoriteWallpaper)
}