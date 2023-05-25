package com.example.cointwo.data.remote.logging

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.Platform.Companion.INFO
import okhttp3.internal.platform.Platform.Companion.WARN
import okhttp3.logging.HttpLoggingInterceptor

class CustomHttpLogger : HttpLoggingInterceptor.Logger {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    override fun log(message: String) {
        val result = message.trim()
        if((result.startsWith("{") && result.endsWith("}")) || (result.startsWith("[") && result.endsWith("]")) ){
            try{
                val prettyString = gson.toJson(JsonParser.parseString(message))
                Platform.get().log(prettyString,INFO,null)
            }catch (e : Exception){
                Platform.get().log(message,WARN,null)
            }
        }else{
            Platform.get().log(message,INFO,null)
        }
    }
}