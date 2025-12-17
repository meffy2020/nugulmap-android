package com.example.neogulmap.domain.model

data class Zone(
    val id: Long,
    val region: String,
    val type: String,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val image: String?
)
