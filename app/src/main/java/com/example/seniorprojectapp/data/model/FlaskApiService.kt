package com.example.seniorprojectapp.data.model

import io.reactivex.Observable
import retrofit2.http.GET

interface FlaskApiService {
    @GET("/getData/")
    fun getData(): Observable<PMData>
}