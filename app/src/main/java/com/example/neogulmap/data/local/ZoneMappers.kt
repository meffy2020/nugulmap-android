package com.example.neogulmap.data.local

import com.example.neogulmap.domain.model.Zone

fun ZoneEntity.toDomain(): Zone {
    return Zone(
        id = this.id,
        region = this.region,
        type = this.type,
        subtype = this.subtype,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude,
        size = this.size,
        address = this.address,
        user = this.user,
        image = this.image,
        name = this.name, // Pass name from entity
        imageUrl = this.imageUrl // Pass imageUrl from entity
    )
}

fun Zone.toEntity(): ZoneEntity {
    return ZoneEntity(
        id = this.id,
        region = this.region,
        type = this.type,
        subtype = this.subtype,
        description = this.description,
        latitude = this.latitude,
        longitude = this.longitude,
        size = this.size,
        address = this.address,
        user = this.user,
        image = this.image,
        name = this.name,
        imageUrl = this.imageUrl
    )
}