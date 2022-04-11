package com.example.myapplication.model.local

import android.content.Context



import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.myapplication.model.entity.Modelweather


private const val DATABASE_NAME = "weather_database"

@TypeConverters(value = [Converters::class])
@Database(entities = [Modelweather::class], version = 1, exportSchema = false)
abstract class weatherDatabase : RoomDatabase() {
    abstract fun weaDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: weatherDatabase? = null

        fun getInstance(context: Context): weatherDatabase {
            return instance
                ?: synchronized(Any()) {
                    instance ?: buildDatabase(
                        context
                    ).also { instance = it }
                }
        }

        private fun buildDatabase(context: Context): weatherDatabase {
            return Room
                .databaseBuilder(
                    context.applicationContext,
                    weatherDatabase::class.java,
                    DATABASE_NAME
                )
                .build()
        }
    }

}