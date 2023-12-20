package ir.araditc.anc.service

interface MqttTraceHandler {

    fun traceDebug(message: String?)

    fun traceError(message: String?)

    fun traceException(message: String?, e: Exception?)
}
