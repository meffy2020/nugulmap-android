package com.example.neogulmap.data.model

data class ZoneDto(
    val id: Long,
    val region: String,
    val type: String, // Outdoor, Booth, etc.
    val subtype: String?,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val size: String?,
    val address: String?,
    val user: String?, // UserId or Name
    val image: String? // Filename
)
