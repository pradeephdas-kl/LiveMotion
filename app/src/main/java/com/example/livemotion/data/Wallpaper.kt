package com.example.livemotion.data

import com.google.firebase.firestore.DocumentId

data class Wallpaper(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val thumbnailUrl: String = "",
    val isFeatured: Boolean = false,
    val createdAt: Long = 0L
)
