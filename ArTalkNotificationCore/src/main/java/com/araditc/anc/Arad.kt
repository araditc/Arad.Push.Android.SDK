package com.araditc.anc

import android.annotation.SuppressLint
import android.content.Context
import com.araditc.anc.data.local.IMessage
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.araditc.anc.data.local.IToken
import com.araditc.anc.data.local.IWakeUp
import com.araditc.anc.data.local.SecureSharedPrefs
import com.araditc.anc.model.FirebaseConfig
import com.araditc.anc.service.amq.AmqAndroidClient
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.UUID
import java.util.concurrent.ExecutionException

object Arad {

    private var firebaseApp: FirebaseApp? = null

    private var iToken: IToken? = null

    private var iWakeUp : IWakeUp? = null

    private var iMessage: IMessage? = null

    @SuppressLint("StaticFieldLeak")
    private lateinit var broker: AmqAndroidClient

    //================================ Arad Init ==================================================>

    fun init(context: Context, firebaseConfig: FirebaseConfig) {
        val option = FirebaseOptions.Builder().setApiKey(firebaseConfig.ApiKey)
            .setApplicationId(firebaseConfig.ApplicationId).setProjectId(firebaseConfig.ProjectId)
            .build();

        firebaseApp = FirebaseApp.initializeApp(context, option, "Arad_SDK_FCM");
    }

    //================================ FCM Method And Interface ==============================================>

    fun getToken(): String? {
        if (firebaseApp == null) return null

        val fcmInstance: FirebaseMessaging = firebaseApp!!.get(FirebaseMessaging::class.java)

        val tokenTask = fcmInstance.token
        return try {
            Tasks.await(tokenTask)
        } catch (e: ExecutionException) {
            throw tokenTask.exception!!
        }
    }

    fun SetIToken(ob: IToken) {
        iToken = ob;
    }

    fun setToken(token: String) {
        if (iToken != null) {
            iToken!!.NewToken(token)
        }
    }

    fun setIWakeUp(ob: IWakeUp){
        iWakeUp = ob
    }

    fun setWakeUp(wakeUp : Boolean){
        if(iWakeUp !=null){
            iWakeUp!!.WakeUp(wakeUp)
        }
    }

    //================================ Broker Setting =============================================>

    fun setConfig(
        context: Context,
        clientUserName: String,
        clientPassword: String,
        connectionURL: String
    ) {
        val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString("clientUserName", clientUserName)
        editor.putString("clientPassword", clientPassword)
        editor.putString("connectionURL", "tcp://$connectionURL:1883")
        editor.apply()
    }

    fun checkConfig(context: Context, appPackageName: String): Boolean {
        val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val connectionUrl = sharedPreferences.getString("connectionURL", "")

        return !connectionUrl.equals("")
    }

    fun getMessage(context: Context){
        connectBroker(context)
    }

    private fun connectBroker(context: Context) {
        val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val serverURI = sharedPreferences.getString("connectionURL", "")

        broker = AmqAndroidClient(context, serverURI!!, UUID.randomUUID().toString())

        broker.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                } else {
                }
            }

            override fun connectionLost(cause: Throwable?) {
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                setMessage(message.payload.toString(Charsets.UTF_8))
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {
            }
        })

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = false
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.userName = sharedPreferences.getString("clientUserName", "")
        mqttConnectOptions.password =
            sharedPreferences.getString("clientPassword", "")!!.toCharArray()

        broker.connect(mqttConnectOptions, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                val disconnectedBufferOptions = DisconnectedBufferOptions()
                disconnectedBufferOptions.isBufferEnabled = true
                disconnectedBufferOptions.bufferSize = 100
                disconnectedBufferOptions.isPersistBuffer = false
                disconnectedBufferOptions.isDeleteOldestMessages = false
                broker.setBufferOpts(disconnectedBufferOptions)

                subscribeToTopicBroker(mqttConnectOptions.userName)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            }
        })
    }

    private fun subscribeToTopicBroker(topic: String) {
        broker.subscribe(topic, 0, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            }
        })
    }

    private fun disconnectBroker (){
        broker.disconnect()
    }

    fun setIMessage(ob: IMessage) {
        iMessage = ob
    }

    fun setMessage(payload: String) {
        if (iMessage != null) {
            iMessage!!.MessageReceive(payload)
        }
    }
}