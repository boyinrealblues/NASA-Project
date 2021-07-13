package com.example.nasa.Data

import com.example.nasa.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APODServiceInstance {
    private val retrofit by lazy{
        Retrofit.Builder().baseUrl(Utils.BaseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }
    val APODApi : APODService by lazy{
        retrofit.create(APODService::class.java)
    }

}