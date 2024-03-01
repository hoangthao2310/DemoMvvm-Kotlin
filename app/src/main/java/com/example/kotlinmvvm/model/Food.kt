package com.example.kotlinmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @PrimaryKey(autoGenerate = true)
    val foodId: String? = null,
    val nameFood: String? = null,
    val price: Double? = null,
    val image: String? = null,
)
