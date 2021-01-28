package com.orbital.sonic.roomdatabasemvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.interfaces.AlarmDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(AlarmEntity::class), version = 1, exportSchema = false)
abstract class AlarmRoomDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: AlarmRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AlarmRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmRoomDatabase::class.java,
                    "alarm_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val alarmDao = database.alarmDao()

                    // Delete all content here.
                    alarmDao.deleteAllAlarm()

                    // Add some data in Room if you want.
                }
            }
        }
    }

}