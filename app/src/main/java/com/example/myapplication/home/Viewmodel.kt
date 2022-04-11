package com.example.myapplication.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.entity.Modelweather
import com.example.myapplication.model.local.WeatherDao
import com.example.myapplication.model.local.weatherDatabase
import com.example.myapplication.model.local.localrepositryImp as localrepositryImp1


class viewmodel(application: Application): AndroidViewModel(application) {

    private var weathermutablelivedata=MutableLiveData<List<Modelweather>>()
  //  private var localrepositryimpp= localrepositryImp1
    init {
    var db= weatherDatabase.getInstance(application)
   // localrepositryimpp= localrepositryImp1(db)
      }

}