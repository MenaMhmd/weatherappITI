package com.example.myapplication.model.local

import com.example.myapplication.model.entity.Modelweather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class localrepositryImp(private val db: weatherDatabase) :  localrepository {
    //control of repository to determine work in mainthread or io thread
    override suspend fun getweather() = withContext(Dispatchers.IO) {
        db.weaDao().getweather()
    }



    override suspend fun insertOrUpdateWeather(model: Modelweather) {
        withContext(Dispatchers.IO) {
            db.weaDao().insertOrUpdateWeather(model)
        }
    }
}