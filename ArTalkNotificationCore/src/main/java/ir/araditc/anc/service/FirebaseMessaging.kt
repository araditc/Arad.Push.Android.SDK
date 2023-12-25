package ir.araditc.anc.service

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.araditc.anc.Arad
import ir.araditc.anc.data.local.SecureSharedPrefs
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.UUID

class FirebaseMessaging : FirebaseMessagingService() {

    private lateinit var mqttAndroidClient: MqttAndroidClient

    private var title: String= ""
    private var content: String= ""
    private var payload: String = ""

    companion object {
        const val TAG = "APN"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        title = remoteMessage.notification?.title.toString()
        content = remoteMessage.notification?.body.toString()
        payload = remoteMessage.data.toString()
        Arad.setMessage(title , content , payload)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Arad.setToken(token);
    }

    fun connect(context: Context) {
        val sharedPreferences = SecureSharedPrefs.getSharedPreferences(context)
        val serverURI = sharedPreferences.getString("connectionURL", "")

        mqttAndroidClient =
            MqttAndroidClient(applicationContext, serverURI!!, UUID.randomUUID().toString())
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                } else {
                }
            }

            override fun connectionLost(cause: Throwable?) {
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                Arad.setMessage(title , content , message.payload.toString())
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

        mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                val disconnectedBufferOptions = DisconnectedBufferOptions()
                disconnectedBufferOptions.isBufferEnabled = true
                disconnectedBufferOptions.bufferSize = 100
                disconnectedBufferOptions.isPersistBuffer = false
                disconnectedBufferOptions.isDeleteOldestMessages = false
                mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)

                subscribeToTopic(mqttConnectOptions.userName)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            }
        })
    }

    fun subscribeToTopic(topic: String) {
        mqttAndroidClient.subscribe(topic, 0, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            }
        })
    }

    private fun disconnect (){
        mqttAndroidClient.disconnect()
    }
}