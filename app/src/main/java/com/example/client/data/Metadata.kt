package com.example.client

import com.google.gson.annotations.SerializedName


data class Metadata (

  @SerializedName("identifier"  ) var identifier  : String? = null,
  @SerializedName("mediatype"   ) var mediatype   : String? = null,
  @SerializedName("collection"  ) var collection  : String? = null,
  @SerializedName("description" ) var description : String? = null,
  @SerializedName("scanner"     ) var scanner     : String? = null,
  @SerializedName("subject"     ) var subject     : String? = null,
  @SerializedName("title"       ) var title       : String? = null,
  @SerializedName("uploader"    ) var uploader    : String? = null,
  @SerializedName("publicdate"  ) var publicdate  : String? = null,
  @SerializedName("addeddate"   ) var addeddate   : String? = null,
  @SerializedName("curation"    ) var curation    : String? = null

)