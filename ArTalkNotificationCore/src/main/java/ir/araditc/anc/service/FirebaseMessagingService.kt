package ir.araditc.anc.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.araditc.anc.Arad

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title

        if (title != null) {
            Arad.setMessage(title)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Arad.setToken(token);
    }
}