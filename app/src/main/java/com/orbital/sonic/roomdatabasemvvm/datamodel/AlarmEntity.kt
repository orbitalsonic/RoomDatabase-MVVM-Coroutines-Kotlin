package com.orbital.sonic.roomdatabasemvvm.datamodel

import androidx.room.*

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true) var id: Int=0,
    @ColumnInfo(name = "alarmTitle") var alarmTitle:String,
    @ColumnInfo(name = "isAlarmOn") var isAlarmOn:Boolean,
    @ColumnInfo(name = "alarmHour") var alarmHour:Int,
    @ColumnInfo(name = "alarmMinute") var alarmMinute:Int,
    @ColumnInfo(name = "amPm") var amPm:String,
    @ColumnInfo(name = "isRepeatAlarm") var isRepeatAlarm:Boolean,
    @ColumnInfo(name = "repeatDays") var repeatDays: String,
    @ColumnInfo(name = "isVibrationAlarm") var isVibrationAlarm:Boolean,
    @ColumnInfo(name = "alarmTone") var alarmTone:String,
    @ColumnInfo(name = "isFadeIn") var isFadeIn:Boolean,
    @ColumnInfo(name = "isFlashLightOn") var isFlashLightOn:Boolean,
    @ColumnInfo(name = "isFlashLightBlinking") var isFlashLightBlinking:Boolean,
    @ColumnInfo(name = "isSnooze") var isSnooze:Boolean
    )
