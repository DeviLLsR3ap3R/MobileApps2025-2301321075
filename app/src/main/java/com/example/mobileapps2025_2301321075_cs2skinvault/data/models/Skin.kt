package com.example.mobileapps2025_2301321075_cs2skinvault.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skins")
data class Skin(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val weapon: String,
    val rarity: String,
    val price: Double,
    val imageUrl: String?
)