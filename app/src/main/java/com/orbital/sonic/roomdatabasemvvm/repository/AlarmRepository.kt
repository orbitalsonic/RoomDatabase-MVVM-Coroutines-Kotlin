package com.orbital.sonic.roomdatabasemvvm.repository

import androidx.annotation.WorkerThread
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.interfaces.AlarmDao
import kotlinx.coroutines.flow.Flow

class AlarmRepository(private val alarmDao: AlarmDao) {

    val allAlarms: Flow<List<AlarmEntity>> = alarmDao.getAllAlarms()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAlarm(entity: AlarmEntity){
        alarmDao.insertAlarm(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateAlarm(entity: AlarmEntity){
        alarmDao.updateAlarm(entity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAlarm(entity: AlarmEntity){
        alarmDao.deleteAlarm(entity)
    }

    @WorkerThread
    fun deleteAllAlarm(){
        alarmDao.deleteAllAlarm()
    }

}