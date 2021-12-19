package com.decode.nextjob.helpers

import android.net.ConnectivityManager
import java.net.InetAddress


object helpers {
    fun isInternetAvailable(): Boolean {
         try {
            val ipAddr = InetAddress.getByName("google.com")
            //You can replace it with your name
             return !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
        return false
    }



}


