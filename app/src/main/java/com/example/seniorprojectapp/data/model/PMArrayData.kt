package com.example.seniorprojectapp.data.model

import com.google.gson.annotations.SerializedName

class PMArrayData {
    @SerializedName("co2")
    var co2:Float? = null

    @SerializedName("humid")
    var humid:Float? = null

    @SerializedName("pm1")
    var pm1:Float? = null

    @SerializedName("pm10")
    var pm10:Float? = null

    @SerializedName("pm25")
    var pm25:Float? = null

    @SerializedName("temp")
    var temp:Float? = null

    @SerializedName("time")
    var time:String? = null
}