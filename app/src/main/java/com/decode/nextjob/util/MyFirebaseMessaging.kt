package com.decode.nextjob.util

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging: FirebaseMessagingService() {

    public val TAG ="MyFirebaseMessagingSer"

    override fun onDeletedMessages() {
        super.onDeletedMessages()


    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        var notificationBody=""
        var notificationTitle=""
        var notificationData=""

        try {
          notificationData= remoteMessage.data.toString()
            notificationTitle= remoteMessage.notification!!.title.toString()
            notificationData= remoteMessage.notification!!.body.toString()

        }catch (e:NullPointerException){
            Log.e("onMessageFirebase",e.message.toString())

        }
        Log.d(TAG,"onMessageFirebase: data : "+notificationData)
        Log.d(TAG,"onMessageFirebase: title : "+notificationTitle)
        Log.d(TAG,"onMessageFirebase: body: "+notificationBody)
    }
}