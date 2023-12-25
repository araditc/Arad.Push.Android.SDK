package com.araditc.anc.service.amq.ping

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.araditc.anc.service.amq.ping.AlarmPingSender
import kotlinx.coroutines.suspendCancellableCoroutine
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import kotlin.coroutines.resume

class PingWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    private val TAG = "APN"
    override suspend fun doWork(): Result = suspendCancellableCoroutine { continuation ->
        Log.i(TAG, "Sending Ping at: ${System.currentTimeMillis()}")
        AlarmPingSender.clientComms?.checkForActivity(object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.i(TAG, "Success.")
                continuation.resume(Result.success())
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.i(TAG, "Failure $exception")
                continuation.resume(Result.failure())
            }
        }) ?: kotlin.run {
            continuation.resume(Result.failure())
        }
    }
}
