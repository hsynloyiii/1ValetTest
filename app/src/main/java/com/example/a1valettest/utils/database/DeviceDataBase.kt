package com.example.a1valettest.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a1valettest.model.DeviceContent

@Database(entities = [DeviceContent::class], version = 1)
abstract class DeviceDataBase : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao

    companion object {
        @Volatile
        private var INSTANCE: DeviceDataBase? = null

        fun getDatabase(
            context: Context
        ): DeviceDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DeviceDataBase::class.java,
                    "device_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }

}