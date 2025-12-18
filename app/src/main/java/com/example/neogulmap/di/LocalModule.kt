package com.example.neogulmap.di

import android.content.Context
import androidx.room.Room
import com.example.neogulmap.data.local.AppDatabase
import com.example.neogulmap.data.local.ZoneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "neogulmap_db"
        ).fallbackToDestructiveMigration() // Migration strategy if schema changes
            .build()
    }

    @Provides
    @Singleton
    fun provideZoneDao(appDatabase: AppDatabase): ZoneDao {
        return appDatabase.zoneDao()
    }
}
