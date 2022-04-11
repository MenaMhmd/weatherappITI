package com.example.myapplication.model.local

import com.example.myapplication.model.entity.Modelweather

interface localrepository {
    suspend fun getweather():List<Modelweather>

    suspend fun insertOrUpdateWeather(model: Modelweather)
}