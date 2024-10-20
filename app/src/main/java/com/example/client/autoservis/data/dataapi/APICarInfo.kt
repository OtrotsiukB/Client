package com.example.client.autoservis.data.dataapi

import com.google.gson.annotations.SerializedName


data class APICarInfo (

    @SerializedName("nomberCar"    ) var nomberCar    : String? = null,
    @SerializedName("nameCustomer" ) var nameCustomer : String? = null,
    @SerializedName("nomberPhone"  ) var nomberPhone  : String? = null,
    @SerializedName("obem"         ) var obem         : String? = null,
    @SerializedName("kw"           ) var kw           : String? = null,
    @SerializedName("vin"          ) var vin          : String? = null,
    @SerializedName("yearCar"      ) var yearCar      : String? = null,
    @SerializedName("createdAt"    ) var createdAt    : String? = null,
    @SerializedName("id"           ) var id           : String? = null

)