package com.example.client.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class retrofit {
    object RetrofitModule {


        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://archive.org/metadata/")   //
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val iRetrofit: iRetrofit = retrofit.create()
    }
}