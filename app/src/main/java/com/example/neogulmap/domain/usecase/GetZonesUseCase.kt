package com.example.neogulmap.domain.usecase

import com.example.neogulmap.domain.model.Zone
import com.example.neogulmap.domain.repository.ZoneRepository
import javax.inject.Inject

class GetZonesUseCase @Inject constructor(
    private val repository: ZoneRepository
) {
    suspend operator fun invoke(): List<Zone> {
        return repository.getZones()
    }
}
