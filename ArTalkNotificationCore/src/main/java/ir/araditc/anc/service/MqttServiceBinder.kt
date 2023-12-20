package ir.araditc.anc.service

import android.os.Binder

class MqttServiceBinder(val service: MqttService) : Binder() {

    var activityToken: String? = null

}
