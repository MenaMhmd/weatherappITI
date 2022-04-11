package com.example.myapplication.model.local

import androidx.room.*
import com.example.myapplication.model.entity.Modelweather

@Dao
interface WeatherDao {

@Insert(onConflict = OnConflictStrategy.REPLACE)

suspend fun insertOrUpdateWeather(model: Modelweather)


@Query("select * from weathertable")
suspend fun getweather():List<Modelweather>


}