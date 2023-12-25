package ir.araditc.ane.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ir.araditc.anc.Arad
import ir.araditc.anc.data.local.IMessage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Example Usage Arad Push Android SDK

        // Get Device Name
        var devicename = Arad.getDeviceName()

        // Get Package Name
        var packageName = Arad.getPackageName(this)

        // Get Device Android Version
        var deviceVersion = Arad.getVersion(this)

        // Get Fcm Token
        Thread {
            val token = Arad.getToken()
            Log.i("Fcm_Token", token.toString());
        }.start()

        // Listener For Get Push Message
        Arad.setIMessage(object : IMessage {
            override fun MessageReceive(title: String, content: String, payload: String) {
                // title message
                Log.i("Title Message", title)

                // content message
                Log.i("Content Message", content)

                // payload message
                Log.i("Payload Message", payload)
            }
        })
    }
}