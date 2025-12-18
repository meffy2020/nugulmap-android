package com.example.neogulmap.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zones")
data class ZoneEntity(
    @PrimaryKey val id: Long,
    val region: String,
    val type: String,
    val subtype: String?,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val size: String?,
    val address: String?,
    val user: String?,
    val image: String?,
    val name: String?, // Added for compatibility
    val imageUrl: String? // Added for compatibility
)
