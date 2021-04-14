package com.example.seniorprojectapp.data.model

import retrofit2.http.GET

interface FlaskApiService {
    @GET("getData/")
    fun getData(): List<PMData>
}