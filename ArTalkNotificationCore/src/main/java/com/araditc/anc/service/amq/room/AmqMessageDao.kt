package com.araditc.anc.service.amq.room

import androidx.room.*
import com.araditc.anc.service.amq.room.entity.AmqMessageEntity

@Dao
interface AmqMessageDao {

    @get:Query("SELECT * FROM AmqMessageEntity")
    val all: List<AmqMessageEntity>

    @Query("SELECT * FROM AmqMessageEntity WHERE clientHandle = :clientHandle ORDER BY timestamp ASC")
    fun allArrived(clientHandle: String): List<AmqMessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(amqMessageEntity: AmqMessageEntity): Long

    @Update
    fun updateAll(vararg amqMessageEntity: AmqMessageEntity)

    @Delete
    fun delete(amqMessageEntity: AmqMessageEntity)

    @Query("DELETE FROM AmqMessageEntity WHERE clientHandle = :clientHandle AND messageId = :id")
    fun deleteId(clientHandle: String, id: String): Int

    @Query("DELETE FROM AmqMessageEntity WHERE clientHandle = :clientHandle")
    fun deleteClientHandle(clientHandle: String): Int

}
