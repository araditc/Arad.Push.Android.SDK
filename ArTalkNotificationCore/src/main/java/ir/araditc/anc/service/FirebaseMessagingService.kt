package ir.araditc.anc.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val json = remoteMessage.data

        val notification = remoteMessage.notification?.title;
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}