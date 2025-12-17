package com.example.neogulmap.data.api

import com.example.neogulmap.data.model.ApiResponse
import com.example.neogulmap.data.model.ZoneDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NugulApi {

    @GET("zones")
    suspend fun getAllZones(): List<ZoneDto> // Spec says "JSON list of zones", usually [{}, {}] directly?
    // Wait, the spec says:
    // Response Format: { "success": boolean, "message": "string", "data": { ... } }
    // BUT for "Get All Zones", it says "JSON list of zones". 
    // And "Get All Zones (Paged)" returns "JSON with zones list and pagination info".
    // "Get Zone" returns wrapped response.
    // I should probably assume the standard wrapper is used everywhere if consistent.
    // BUT standard REST practices might return list directly.
    // Let's assume the wrapper for now or generic list if it fails.
    // Spec says: "Most endpoints return a JSON response with the following structure".
    // Let's stick to the wrapper where possible.
    
    // Actually, re-reading: "Most endpoints return a JSON response...".
    // "Get All Zones": "JSON list of zones". This might mean List<ZoneDto>.
    // Let's assume List<ZoneDto> for getAllZones based on "JSON list of zones".
    // For "Get Zone": "{ success: true, data: { zone: ... } }".
    
    @GET("zones")
    suspend fun getAllZonesList(): List<ZoneDto>

    @GET("zones/paged")
    suspend fun getZonesPaged(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResponse<PagedZoneData>

    @GET("oauth2/login-urls")
    suspend fun getLoginUrls(): ApiResponse<LoginUrlsData>
}

data class PagedZoneData(
    val content: List<ZoneDto>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long
)

data class LoginUrlsData(
    val loginUrls: Map<String, String>
)
