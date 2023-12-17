package com.example.client.network

import com.example.client.APIArhiveTest2
import com.example.client.APIArhiveTest3
import com.example.client.APIarhive
import retrofit2.http.GET
import retrofit2.http.Path

interface iRetrofit {

    @GET("{id}")
    suspend fun getinfo(@Path("id") id: String ): APIarhive
    @GET("{id}")
    suspend fun getinfoTest(@Path("id") id: String ): APIArhiveTest2
    @GET("{id}")
    suspend fun getinfoTest3(@Path("id") id: String ): APIArhiveTest3

}