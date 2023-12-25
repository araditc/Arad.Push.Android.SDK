package com.araditc.anc.service.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.araditc.anc.Arad

class FcmMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Arad.setWakeUp(true)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Arad.setToken(token);
    }
}