package com.example.client.autoservis.network

import com.example.client.network.iRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class retrofitAutoservis {
    object RetrofitModule {


        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://46.175.146.123:8080")   //
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val iRetrofitAutoservis: iRetrofitAutoservis = retrofit.create()
    }
}


