package com.example.client.autoservis.network

import com.example.client.APIarhive
import com.example.client.autoservis.data.dataapi.APICarInfo
import com.example.client.autoservis.data.dataapi.APIservistabel
import com.example.client.autoservis.data.dataapi.APIusertabel
import retrofit2.http.*

interface iRetrofitAutoservis {
    @GET("/user/byName")
    suspend fun getUser(@Query("nameUser") nameUser: String ): List<APIusertabel>
    @GET("/servistabel/all")
    suspend fun getServis(): List<APIservistabel>

    @POST("/car/add")
    suspend fun addCar(@Body car:APICarInfo):APICarInfo
    @GET("/car/getCarById")
    suspend fun getCarById(id:String):APICarInfo?
    @GET("/car/search")
    suspend fun getCarsearch(nomer:String):List<APICarInfo>


}