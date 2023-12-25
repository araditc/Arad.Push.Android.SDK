package com.araditc.anc.service.amq

import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * Implementation of the IMqttDeliveryToken interface for use from within the MqttAndroidClient implementation
 */
internal class AmqDeliveryTokenAndroid(
    client: AmqAndroidClient,
    userContext: Any?,
    listener: IMqttActionListener?, // The message which is being tracked by this token
    private var message: MqttMessage
) : AmqTokenAndroid(client, userContext, listener), IMqttDeliveryToken {

    override fun getMessage() = message

    fun setMessage(message: MqttMessage) {
        this.message = message
    }

    fun notifyDelivery(delivered: MqttMessage) {
        message = delivered
        super.notifyComplete()
    }
}
