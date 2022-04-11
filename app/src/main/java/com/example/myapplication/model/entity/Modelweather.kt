package com.example.myapplication.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="weathertable")
data class Modelweather (

    @PrimaryKey @ColumnInfo(name ="cityname")
     var cityname:String,
    @ColumnInfo(name ="temp")
    var temp:String,
    @ColumnInfo(name ="tempmin")
    var tempmin:String,
    @ColumnInfo(name ="tempmax")
    var tempmax:String,
    @ColumnInfo(name ="pressure")
    var pressure:String,
    @ColumnInfo(name ="humidity")
    var humidity:String,
    @ColumnInfo(name ="description")
    var description:String,
    @ColumnInfo(name ="speed")
    var speed:String)
