package ir.araditc.ane.ui

import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ir.araditc.anc.Arad
import ir.araditc.anc.data.local.IMessage
import ir.araditc.anc.service.FirebaseMessagingService


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Example
        var devicename = Arad.getDeviceName()

        var packageName = Arad.getPackageName(this)

        var deviceVersion = Arad.getVersion(this)

        Thread {
            val token = Arad.getToken()
        }.start()

        Arad.setIMessage(object : IMessage {
            override fun MessageReceive(title: String, content: String, payload: String) {
            }
        })
    }
}