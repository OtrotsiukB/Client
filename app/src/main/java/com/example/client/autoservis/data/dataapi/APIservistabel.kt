package com.example.client.autoservis.data.dataapi

import com.google.gson.annotations.SerializedName


data class APIservistabel (

    @SerializedName("createdAt"  ) var createdAt  : String? = null,
    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("nameServis" ) var nameServis : String? = null,
    @SerializedName("access"     ) var access     : String? = null,
    @SerializedName("limitUsers" ) var limitUsers : String? = null,
    @SerializedName("urlIcon"    ) var urlIcon    : String? = null

)