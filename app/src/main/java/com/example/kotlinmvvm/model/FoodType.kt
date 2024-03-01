package com.example.kotlinmvvm.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodType(
    @PrimaryKey(autoGenerate = true)
    val typeId: String? = null,
    val name: String? = null,
    val image: String? = null,
)