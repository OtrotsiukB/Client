package com.example.client.network

import com.example.client.APIarhive
import retrofit2.http.GET
import retrofit2.http.Path

interface iRetrofit {

    @GET("{id}")
    suspend fun getinfo(@Path("id") id: String ): APIarhive
}