package com.orbital.sonic.roomdatabasemvvm.interfaces

interface OnAlarmClickListener {
    fun onItemClick(position: Int)
    fun onDeleteClick(position: Int)
    fun onSwitchClick(position: Int)
}