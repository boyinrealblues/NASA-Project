package com.example.nasa.Data

import retrofit2.http.GET
import retrofit2.http.Query

interface APODService
{
    @GET("apod")
    suspend fun getAPOD(@Query("api_key") key : String):APOD

    @GET("apod")
    suspend fun getAPODList(@Query("api_key") key : String , @Query("start_date") dateStart : String ): List<APOD>
}