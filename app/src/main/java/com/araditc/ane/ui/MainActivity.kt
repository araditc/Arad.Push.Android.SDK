package com.araditc.ane.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.araditc.anc.Arad
import com.araditc.anc.data.local.IMessage
import com.araditc.anc.data.local.IToken
import com.araditc.anc.data.local.IWakeUp
import com.araditc.anc.utill.DeviceUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //================================= Device Util ============================================
        var devicename = DeviceUtils.getDeviceName()
        var packageName = DeviceUtils.getPackageName(this)
        var deviceVersion = DeviceUtils.getVersion(this)
        //================================ FCM Implementation ======================================

        Thread {
            val token = Arad.getToken()
            Log.i("Fcm_Token", token.toString())
        }.start()

        Arad.SetIToken(object : IToken {
            override fun NewToken(token: String) {
                Log.i("APN", "This Method Return New Token If Generate From FCM")
            }
        })

        //================================ APN Implementation ======================================

        // This Method Call For Message
        Arad.setConfig(this , "{USERNAME}" , "{PASSWORD}" , "{URL}")
        Arad.getMessage(this@MainActivity);
        Arad.setIMessage(object : IMessage {
            override fun MessageReceive(payload: String) {
                Log.i("APN", "Message Receive If Message Exist For This User In Server")
            }
        })
        Arad.setIWakeUp(object : IWakeUp {
            override fun WakeUp(wakeUp: Boolean) {
                Log.i("APN", "Wake Up App From FCM For Get Message From APN")
                Arad.getMessage(this@MainActivity)
            }
        })
    }
}