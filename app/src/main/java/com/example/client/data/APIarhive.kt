package com.example.client

import com.google.gson.annotations.SerializedName


data class APIarhive (

  @SerializedName("created"           ) var created         : Int?              = null,
  @SerializedName("d1"                ) var d1              : String?           = null,
  @SerializedName("d2"                ) var d2              : String?           = null,
  @SerializedName("dir"               ) var dir             : String?           = null,
  @SerializedName("files"             ) var files           : ArrayList<Files>  = arrayListOf(),
  @SerializedName("files_count"       ) var filesCount      : Int?              = null,
  @SerializedName("item_last_updated" ) var itemLastUpdated : Int?              = null,
  @SerializedName("item_size"         ) var itemSize        : Int?              = null,
  @SerializedName("metadata"          ) var metadata        : Metadata?         = Metadata(),
  @SerializedName("server"            ) var server          : String?           = null,
  @SerializedName("uniq"              ) var uniq            : Int?              = null,
  @SerializedName("workable_servers"  ) var workableServers : ArrayList<String> = arrayListOf()

)