package com.example.seniorprojectapp.presentation.provider

import android.util.Log
import com.example.seniorprojectapp.data.model.FlaskApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkProvider(private val url:String?) {

    fun provideRetrofit(): Retrofit {
        Log.d("Final Url", url)
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.client(OkHttpClient.Builder().build()).build()
    }


    fun provideFlaskAPIClient(): FlaskApiService {
        return provideRetrofit().create(FlaskApiService::class.java)
    }
}