package com.example.client

object ConvetorFiles2ToFiles {
    fun convert(filesIN: MutableList<Files2>):MutableList<Files>{
        var listFiles: MutableList<Files> = mutableListOf()
        for (x in filesIN){
            var temp = Files()
            temp.name = x.name
            temp.title = x.title
            listFiles.add(temp)
        }
        return listFiles
    }
}