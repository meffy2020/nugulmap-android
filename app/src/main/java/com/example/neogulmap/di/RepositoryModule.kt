package com.example.neogulmap.di

import com.example.neogulmap.data.repository.ZoneRepositoryImpl
import com.example.neogulmap.domain.repository.ZoneRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindZoneRepository(
        zoneRepositoryImpl: ZoneRepositoryImpl
    ): ZoneRepository
}
