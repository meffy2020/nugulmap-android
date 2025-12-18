package com.example.neogulmap.data.repository

import com.example.neogulmap.data.api.NugulApi
import com.example.neogulmap.data.local.ZoneDao
import com.example.neogulmap.data.local.toDomain
import com.example.neogulmap.data.local.toEntity
import com.example.neogulmap.domain.model.Zone
import com.example.neogulmap.domain.repository.ZoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ZoneRepositoryImpl @Inject constructor(
    private val api: NugulApi,
    private val zoneDao: ZoneDao
) : ZoneRepository {

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

    override suspend fun getZonesByRadius(latitude: Double, longitude: Double, radius: Int): Flow<Result<List<Zone>>> = flow {
        // 1. Emit cached data immediately if available
        zoneDao.getAllZones().map { entities -> entities.map { it.toDomain() } }
            .collect { cachedZones ->
                if (cachedZones.isNotEmpty()) {
                    emit(Result.success(cachedZones))
                }
            }

        // 2. Try to fetch from network
        try {
            val apiResponse = api.getAllZonesList() // Note: current API call doesn't use radius. Needs server update

            if (apiResponse.success && apiResponse.data != null) {
                val dtoList = apiResponse.data.zones
                    ?: apiResponse.data.content
                    ?: apiResponse.data.zoneList
                    ?: apiResponse.data.list
                    ?: apiResponse.data.data
                    ?: apiResponse.data.result
                    ?: emptyList()

                val domainList = dtoList.map { mapToDomain(it) }

                // 3. Update local DB with fresh data. Room's Flow will automatically emit this update to collectors.
                withContext(Dispatchers.IO) {
                    zoneDao.deleteAll()
                    zoneDao.insertAll(domainList.map { it.toEntity() })
                }

            } else {
                // API returned failure, but network call was successful
                val errorMessage = apiResponse.message ?: "Unknown API error"
                // Only emit failure if no cached data was available
                if (zoneDao.getAllZones().first().isEmpty()) { // Check if cache is still empty after attempted network fetch
                    emit(Result.failure(Exception(errorMessage)))
                }
            }
        } catch (e: Exception) {
            // Network error
            // Only emit failure if no cached data was available
            if (zoneDao.getAllZones().first().isEmpty()) { // Check if cache is empty
                emit(Result.failure(Exception("Network error: ${e.message}")))
            } else {
                // If cached data was emitted, and network failed, just log error but keep showing cached.
                // emit(Result.failure(Exception("Network error: ${e.message}. Showing cached data."))) // Optionally emit a warning with cached data
                e.printStackTrace()
            }
        }
    }.catch { e ->
        // Catch any exceptions in the flow itself
        emit(Result.failure(e))
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