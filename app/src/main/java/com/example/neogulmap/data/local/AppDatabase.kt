package com.example.neogulmap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ZoneEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun zoneDao(): ZoneDao
}
