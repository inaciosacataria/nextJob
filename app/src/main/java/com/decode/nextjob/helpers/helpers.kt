package com.decode.nextjob.helpers

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import java.net.InetAddress


object helpers {
    fun isInternetAvailable(): Boolean {
         try {
            val ipAddr = InetAddress.getByName("google.com")
            //You can replace it with your name
             return !ipAddr.equals("")
        } catch (e: Exception) {
             return false
        }

    }



}


