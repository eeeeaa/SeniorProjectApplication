package com.example.seniorprojectapp.presentation.model

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class ArrayDataModel(pm25_in: Float?, datetime_in: String?){
    val pm25:Float?
    val datetime:String?
    init {
        pm25 = pm25_in
        datetime = convertDateFormat(datetime_in)
        Log.d("ArrayData", "datetime original: " + datetime_in)
        Log.d("ArrayData", "datetime: " + datetime)
    }
    private fun convertDateFormat(datetime: String?):String{
        //val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        //dateFormat.timeZone = TimeZone.getTimeZone("Thailand")
        val date1 = datetime?.substring(0,10)
        val date2 = datetime?.substring(11,19)
        val date_final = date1 + " " + date2
        //val date = dateFormat.parse(datetime) //You will get date object relative to server/client timezone wherever it is parsed
        //val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //If you need time just put specific format for time like 'HH:mm:ss'
        //val dateStr = formatter.format(date)
        return date_final
    }
}