package com.araditc.anc.service.amq

enum class QoS(val value: Int) {

    AtMostOnce(0), AtLeastOnce(1), ExactlyOnce(2);

    companion object {
        @JvmStatic
        fun valueOf(qos: Int): QoS {
            return values()[qos]
        }
    }

}