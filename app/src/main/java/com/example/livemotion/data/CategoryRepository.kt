package com.example.livemotion.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val categoriesCollection = firestore.collection("categories")

    suspend fun getCategories(): List<Category> {
        return try {
            categoriesCollection
                .get()
                .await()
                .toObjects(Category::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
