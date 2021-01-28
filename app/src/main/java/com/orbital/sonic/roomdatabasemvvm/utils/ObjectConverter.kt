package com.orbital.sonic.roomdatabasemvvm.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.datamodel.RepeatAlarmItem

object ObjectConverter {
    fun fromListToString(stat: ArrayList<RepeatAlarmItem>): String {
        return Gson().toJson(stat)
    }

    fun fromStringToList(jsonDays: String): ArrayList<RepeatAlarmItem> {
        val notesType = object : TypeToken<ArrayList<RepeatAlarmItem>>() {}.type
        return Gson().fromJson(jsonDays, notesType)
    }

    fun fromObjectToString(mObject: AlarmEntity):String{
        return Gson().toJson(mObject)
    }

    fun fromStringToObject(mString:String): AlarmEntity {
        val noteType = object :TypeToken<AlarmEntity>(){}.type
        return Gson().fromJson(mString,noteType)
    }
}