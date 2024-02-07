package com.araditc.anc.service.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.araditc.anc.Arad

class FcmMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i("APN" , "Fcm WakeUp")
        Arad.setWakeUp(true)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Arad.setToken(token);
    }
}