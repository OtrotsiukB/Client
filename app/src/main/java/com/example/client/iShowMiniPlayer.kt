package com.example.client

import android.opengl.Visibility

interface iShowMiniPlayer {
    fun miniPlayerOffVisible()
    fun miniPlayerOnVisible()
    fun miniPlayerStatusVisible():Int
    fun nowPlaing():Boolean
    fun nowPlaing(status:Boolean)

    fun setIMiniPlayer(i:iMiniPlayer)
    fun getIMiniPlayer():iMiniPlayer?
}