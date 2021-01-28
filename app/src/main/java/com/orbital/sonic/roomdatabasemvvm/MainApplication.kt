package com.orbital.sonic.roomdatabasemvvm

import android.app.Application
import com.orbital.sonic.roomdatabasemvvm.database.AlarmRoomDatabase
import com.orbital.sonic.roomdatabasemvvm.repository.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AlarmRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { AlarmRepository(database.alarmDao()) }
}