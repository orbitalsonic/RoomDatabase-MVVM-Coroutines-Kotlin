package com.orbital.sonic.roomdatabasemvvm.interfaces

import androidx.room.*
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm_table")
    fun getAllAlarms():Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity)

    @Delete
    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    @Update
    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    @Query("DELETE FROM alarm_table")
    fun deleteAllAlarm()

}