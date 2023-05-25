package com.example.cointwo.utils

import android.util.Log
import timber.log.Timber

class Logger {

    companion object{
        var tag = "dodo22"
        fun d(log : String) {
            Log.d(tag, " $log")
//            Timber.tag("$tag ").d(" %s", log)
        }

//        fun setTag(updateTag : String){
//            tag = updateTag
//        }
    }
}