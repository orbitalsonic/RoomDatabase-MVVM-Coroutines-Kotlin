package com.orbital.sonic.roomdatabasemvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orbital.sonic.roomdatabasemvvm.R
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.interfaces.OnAlarmClickListener

class AlarmListAdapter : ListAdapter<AlarmEntity, AlarmListAdapter.AlarmViewHolder>(
    REMINDER_COMPARATOR
) {

    var mListener: OnAlarmClickListener? = null

    fun setOnItemClickListener(listener: OnAlarmClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_recycler_item, parent, false)
        return AlarmViewHolder(view,mListener!!)

    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val current = getItem(position)
        val mTime="${current.alarmHour}:${current.alarmMinute} ${current.amPm}"
        holder.bind(mTime,current.alarmTitle,current.isAlarmOn)
    }


    class AlarmViewHolder(itemView: View,listener: OnAlarmClickListener) : RecyclerView.ViewHolder(itemView) {
        private val alarmTitleName: TextView = itemView.findViewById(R.id.alarmTitleName)
        private val alarmTimeTitle: TextView = itemView.findViewById(R.id.alarmTimeTitle)
        private val deleteAlarm: ImageButton =itemView.findViewById(R.id.deleteAlarm)
        private val alarmOnOffImage:ImageView =itemView.findViewById(R.id.alarmOnOffImage)
        private val btnOnOffAlarm:RelativeLayout = itemView.findViewById(R.id.btnOnOffAlarm)

        init {
            deleteAlarm.setOnClickListener{
                val mPosition=adapterPosition
                listener.onDeleteClick(mPosition)
            }

            itemView.setOnClickListener{
                val mPosition = adapterPosition
                listener.onItemClick(mPosition)
            }

            btnOnOffAlarm.setOnClickListener{
                val mPosition = adapterPosition
                listener.onSwitchClick(mPosition)
            }

        }

        fun bind(mTime: String,title:String,alarmOnOff:Boolean) {
            alarmTitleName.text = title
            alarmTimeTitle.text =mTime
            if (alarmOnOff){
                alarmOnOffImage.setImageResource(R.drawable.ic_repeat_on)
            }else{
                alarmOnOffImage.setImageResource(R.drawable.ic_repeat_off)
            }
        }

    }

    companion object {
        private val REMINDER_COMPARATOR = object : DiffUtil.ItemCallback<AlarmEntity>() {
            override fun areItemsTheSame(oldItem: AlarmEntity, newItem: AlarmEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: AlarmEntity, newItem: AlarmEntity): Boolean {
                return oldItem.alarmHour==newItem.alarmHour && oldItem.alarmMinute==newItem.alarmMinute
            }
        }
    }

//    class AlarmComparator : DiffUtil.ItemCallback<AlarmEntity>() {
//        override fun areItemsTheSame(oldItem: AlarmEntity, newItem: AlarmEntity): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: AlarmEntity, newItem: AlarmEntity): Boolean {
//            return oldItem.alarmHour==newItem.alarmHour && oldItem.alarmMinute==newItem.alarmMinute
//        }
//
//    }
}