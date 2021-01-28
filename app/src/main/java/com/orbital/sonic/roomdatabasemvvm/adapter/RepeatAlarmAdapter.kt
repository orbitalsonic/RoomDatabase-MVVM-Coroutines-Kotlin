package com.orbital.sonic.roomdatabasemvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.orbital.sonic.roomdatabasemvvm.R
import com.orbital.sonic.roomdatabasemvvm.datamodel.RepeatAlarmItem
import com.orbital.sonic.roomdatabasemvvm.interfaces.OnItemClickListener
import java.util.*
import kotlin.collections.ArrayList

class RepeatAlarmAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDaysList: ArrayList<RepeatAlarmItem> = ArrayList()
    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepeatAlarmViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.repeate_alarm_item, parent, false)
        return RepeatAlarmViewHolder(v, mListener!!)
    }

    override fun getItemCount(): Int {
        return mDaysList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem: RepeatAlarmItem = mDaysList[position]
        val viewHolder = holder as RepeatAlarmViewHolder

        viewHolder.dayTextView.text = currentItem.mDay
        if (currentItem.dayState){
            viewHolder.repeatItemLayout.background = ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.bg_repeat_days);
            viewHolder.dayTextView.setTextColor(ContextCompat.getColor(viewHolder.itemView.context,R.color.white))
        }else{
            viewHolder.repeatItemLayout.background = ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.bg_repeat_days2);
            viewHolder.dayTextView.setTextColor(ContextCompat.getColor(viewHolder.itemView.context,R.color.teal_700))
        }


    }

    class RepeatAlarmViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var dayTextView: TextView = itemView.findViewById(R.id.weekDays)!!
        var repeatItemLayout: RelativeLayout = itemView.findViewById(R.id.repeatItemLayout)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }

    }

    fun setAlarmList(mDaysList: ArrayList<RepeatAlarmItem>) {
        this.mDaysList = mDaysList
        notifyDataSetChanged()
    }
}

