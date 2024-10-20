package com.example.client.autoservis.i_nterface

import com.example.client.autoservis.data.dataapi.APIservistabel
import com.example.client.autoservis.data.dataapi.APIusertabel

interface iMainActivity {

    fun setUser(user:APIusertabel)
    fun getUser():APIusertabel
    fun setServis(servis:APIservistabel)
    fun getServis():APIservistabel
}