package com.example.seniorprojectapp.data.model

import com.google.gson.annotations.SerializedName

class PMData {
    @SerializedName("fire")
    var fire:Float? = null

    @SerializedName("humidity")
    var humidity:Float? = null

    @SerializedName("pm_current")
    var pm_current:Float? = null

    @SerializedName("temp")
    var temp:Float? = null

    @SerializedName("time")
    var time:String? = null

    @SerializedName("traffic")
    var traffic:Float? =null

    @SerializedName("windspeed")
    var windspeed:Float? = null

}