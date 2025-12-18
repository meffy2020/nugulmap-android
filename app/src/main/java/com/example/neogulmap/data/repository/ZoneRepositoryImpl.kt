package com.example.neogulmap.data.repository

import com.example.neogulmap.data.api.NugulApi
import com.example.neogulmap.data.local.ZoneDao
import com.example.neogulmap.data.local.toDomain
import com.example.neogulmap.data.local.toEntity
import com.example.neogulmap.domain.model.Zone
import com.example.neogulmap.domain.repository.ZoneRepository
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class ZoneRepositoryImpl @Inject constructor(
    private val api: NugulApi,
    private val zoneDao: ZoneDao // Inject ZoneDao
) : ZoneRepository {

    // Helper to map DTO to Domain (remains the same)
    private fun mapToDomain(dto: com.example.neogulmap.data.model.ZoneDto): Zone {
        return Zone(
            id = dto.id,
            region = dto.region ?: "Unknown Region",
            type = dto.type ?: "Unknown Type",
            subtype = dto.subtype,
            description = dto.description,
            latitude = dto.latitude,
            longitude = dto.longitude,
            size = dto.size,
            address = dto.address,
            user = dto.user,
            image = dto.image,
            name = dto.name,
            imageUrl = dto.imageUrl
        )
    }

    override suspend fun getZonesByRadius(latitude: Double, longitude: Double, radius: Int): Result<List<Zone>> {
        return try {
            // Try to fetch from network
            val apiResponse = api.getAllZonesList()
            
            if (apiResponse.success && apiResponse.data != null) {
                val dtoList = apiResponse.data.zones 
                    ?: apiResponse.data.content 
                    ?: apiResponse.data.zoneList
                    ?: apiResponse.data.list
                    ?: apiResponse.data.items
                    ?: apiResponse.data.data
                    ?: apiResponse.data.result
                    ?: emptyList()
                
                val domainList = dtoList.map { mapToDomain(it) }
                
                // Save to local DB
                zoneDao.deleteAll() // Clear old data
                zoneDao.insertAll(domainList.map { it.toEntity() })
                
                Result.success(domainList)
            } else {
                // Network call was successful but API returned failure
                val localData = zoneDao.getAllZones().first().map { it.toDomain() }
                if (localData.isNotEmpty()) {
                    Result.success(localData) // Return cached data
                } else {
                    Result.failure(Exception(apiResponse.message ?: "Unknown API error and no local data"))
                }
            }
        } catch (e: Exception) {
            // Network call failed, try to get from local DB
            val localData = zoneDao.getAllZones().first().map { it.toDomain() }
            if (localData.isNotEmpty()) {
                Result.success(localData) // Return cached data
            } else {
                Result.failure(Exception("Network error: ${e.message} and no local data"))
            }
        }
    }

    override suspend fun createZone(
        latitude: Double,
        longitude: Double,
        name: String,
        address: String,
        type: String,
        userId: String,
        imageFile: File?
    ): Result<Zone> {
        // Not implemented yet - returning a failure or dummy
        return Result.failure(Exception("Create Zone not implemented yet"))
    }
}