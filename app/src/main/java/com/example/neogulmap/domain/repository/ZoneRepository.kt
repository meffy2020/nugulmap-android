package com.example.neogulmap.domain.repository

import com.example.neogulmap.domain.model.Zone

interface ZoneRepository {
    suspend fun getZones(): List<Zone>
}
