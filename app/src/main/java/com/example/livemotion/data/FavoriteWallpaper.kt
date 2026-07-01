package com.example.livemotion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_wallpapers")
data class FavoriteWallpaper(

    @PrimaryKey
    val wallpaperId: String

)