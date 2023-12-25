package com.araditc.anc.service.amq.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.araditc.anc.service.amq.QoS
import com.araditc.anc.service.amq.room.AmqMessageDatabase.Companion.MQ_DB_VERSION
import com.araditc.anc.service.amq.room.entity.AmqMessageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.*

@Database(entities = [AmqMessageEntity::class], version = MQ_DB_VERSION)
@TypeConverters(Converters::class)
abstract class AmqMessageDatabase : RoomDatabase() {

    abstract fun persistenceDao(): AmqMessageDao

    fun storeArrived(clientHandle: String, topic: String, message: MqttMessage): String {
        val id = UUID.randomUUID().toString()
        val messageArrived = AmqMessageEntity(
            id,
            clientHandle,
            topic,
            MqttMessage(message.payload),
            QoS.valueOf(message.qos),
            message.isRetained,
            message.isDuplicate,
            System.currentTimeMillis()
        )
        CoroutineScope(Dispatchers.IO).launch {
            persistenceDao().insert(messageArrived)
        }
        return id
    }

    fun discardArrived(clientHandle: String, id: String): Boolean {
        var result = false
        CoroutineScope(Dispatchers.IO).launch {
            val queue = async(Dispatchers.IO) {
                persistenceDao().deleteId(clientHandle, id) == 1
            }
            result = queue.await()
        }
        return result
    }

    companion object {

        const val MQ_DB_VERSION = 1

        @Volatile
        private var instance: AmqMessageDatabase? = null

        @Synchronized
        fun getDatabase(context: Context, storageName: String = "messageMQ"): AmqMessageDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context.applicationContext, storageName).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context, storageName: String): AmqMessageDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AmqMessageDatabase::class.java, storageName
            ).build()
        }
    }
}
