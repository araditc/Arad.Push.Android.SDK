package com.araditc.anc.service.amq

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import org.eclipse.paho.client.mqttv3.MqttMessage

class ParcelableMqttMessage : MqttMessage, Parcelable {

    var messageId: String? = null

    internal constructor(original: MqttMessage) : super(original.payload) {
        qos = original.qos
        isRetained = original.isRetained
        isDuplicate = original.isDuplicate
    }

    internal constructor(parcel: Parcel) : super(parcel.createByteArray()) {
        qos = parcel.readInt()
        val flags = parcel.createBooleanArray()
        isRetained = flags!![0]
        isDuplicate = flags[1]
        messageId = parcel.readString()
    }

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByteArray(payload)
        parcel.writeInt(qos)
        parcel.writeBooleanArray(booleanArrayOf(isRetained, isDuplicate))
        parcel.writeString(messageId)
    }

    companion object CREATOR : Creator<ParcelableMqttMessage> {
        override fun createFromParcel(parcel: Parcel): ParcelableMqttMessage {
            return ParcelableMqttMessage(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableMqttMessage?> {
            return arrayOfNulls(size)
        }
    }
}