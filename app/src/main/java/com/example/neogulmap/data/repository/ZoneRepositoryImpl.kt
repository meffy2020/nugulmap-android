package com.example.neogulmap.data.repository

import com.example.neogulmap.data.api.NugulApi
import com.example.neogulmap.domain.model.Zone
import com.example.neogulmap.domain.repository.ZoneRepository
import javax.inject.Inject

class ZoneRepositoryImpl @Inject constructor(
    private val api: NugulApi
) : ZoneRepository {
    override suspend fun getZones(): List<Zone> {
        return try {
            api.getAllZonesList().map { dto ->
                Zone(
                    id = dto.id,
                    region = dto.region,
                    type = dto.type,
                    description = dto.description,
                    latitude = dto.latitude,
                    longitude = dto.longitude,
                    image = dto.image
                )
            }
        } catch (e: Exception) {
            // Handle error or return empty list for now
            e.printStackTrace()
            emptyList()
        }
    }
}
