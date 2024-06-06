package com.example.client.autoservis.data.dataapi


import com.google.gson.annotations.SerializedName


data class APIusertabel (

    @SerializedName("createdAt"   ) var createdAt   : String? = null,
    @SerializedName("id"          ) var id          : String? = null,
    @SerializedName("idServis"    ) var idServis    : String? = null,
    @SerializedName("idShop"      ) var idShop      : String? = null,
    @SerializedName("nameUser"    ) var nameUser    : String? = null,
    @SerializedName("paswordUser" ) var paswordUser : String? = null,
    @SerializedName("role"        ) var role        : String? = null,
    @SerializedName("access"      ) var access      : String? = null

)