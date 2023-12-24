package com.example.client.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookInfo(
    val name:String,
    val urlImage:String,
    val autor:String,
    val year:String,
    val duration: String,
    val infoOfBook:String,
    val cycle: String = "",
    val numberCycle:String,
    val genre: List<String>,
    val idInArhive: String,
    val raitingPlus:Int = 0,
    val raitingMinus:Int =0,
    val viewUser:Int = 0

): Parcelable
