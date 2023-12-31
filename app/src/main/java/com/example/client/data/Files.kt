package com.example.client

import com.google.gson.annotations.SerializedName


data class Files (

  @SerializedName("name"    ) var name    : String? = null,
  @SerializedName("source"  ) var source  : String? = null,
  @SerializedName("mtime"   ) var mtime   : String? = null,
  @SerializedName("size"    ) var size    : String? = null,
  @SerializedName("md5"     ) var md5     : String? = null,
  @SerializedName("crc32"   ) var crc32   : String? = null,
  @SerializedName("sha1"    ) var sha1    : String? = null,
  @SerializedName("format"  ) var format  : String? = null,
  @SerializedName("length"  ) var length  : String? = null,
  @SerializedName("title"   ) var title   : String? = "---",
  @SerializedName("creator" ) var creator : String? = null,
  @SerializedName("album"   ) var album   : String? = null,
  @SerializedName("track"   ) var track   : String? = null,
  @SerializedName("artist"  ) var artist  : String? = null,
  @SerializedName("genre"   ) var genre   : String? = null,
  @SerializedName("comment" ) var comment : String? = null

)