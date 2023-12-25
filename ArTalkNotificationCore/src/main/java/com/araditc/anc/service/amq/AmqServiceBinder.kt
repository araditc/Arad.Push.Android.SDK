package com.araditc.anc.service.amq

import android.os.Binder

class AmqServiceBinder(val service: AmqService) : Binder() {

    var activityToken: String? = null

}
