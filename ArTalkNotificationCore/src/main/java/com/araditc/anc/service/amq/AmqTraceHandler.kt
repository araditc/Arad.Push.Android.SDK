package com.araditc.anc.service.amq

interface AmqTraceHandler {

    fun traceDebug(message: String?)

    fun traceError(message: String?)

    fun traceException(message: String?, e: Exception?)
}
