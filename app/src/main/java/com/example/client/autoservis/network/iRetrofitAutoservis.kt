package com.example.client.autoservis.network

import com.example.client.APIarhive
import com.example.client.autoservis.data.dataapi.APIusertabel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface iRetrofitAutoservis {
    @GET("/user/byName")
    suspend fun getUser(@Query("nameUser") nameUser: String ): List<APIusertabel>
}