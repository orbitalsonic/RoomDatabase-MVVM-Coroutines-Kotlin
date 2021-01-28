package com.orbital.sonic.roomdatabasemvvm.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.observe
import com.orbital.sonic.roomdatabasemvvm.MainApplication
import com.orbital.sonic.roomdatabasemvvm.R
import com.orbital.sonic.roomdatabasemvvm.adapter.AlarmListAdapter
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.interfaces.OnAlarmClickListener
import com.orbital.sonic.roomdatabasemvvm.utils.ObjectConverter
import com.orbital.sonic.roomdatabasemvvm.viewmodel.AlarmViewModel
import com.orbital.sonic.roomdatabasemvvm.viewmodel.AlarmViewModelFactory

class AlarmActivity : BaseActivity() {

    companion object{
        const val ALARM_ADD_REQUEST_CODE = 1
        const val ALARM_EDIT_REQUEST_CODE = 2
    }

    private val alarmViewModel: AlarmViewModel by viewModels {
        AlarmViewModelFactory((application as MainApplication).repository)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: AlarmListAdapter
    private lateinit var tvNoAlarm:TextView

    private lateinit var mAlarmEntity: AlarmEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        initViews()
        createRecyclerView()

        alarmViewModel.allAlarms.observe(owner = this) { alarmList ->
            alarmList.let {
                mAdapter.submitList(it)
                if (alarmList.isEmpty()){
                    tvNoAlarm.visibility=View.VISIBLE
                }else{
                    tvNoAlarm.visibility=View.GONE
                }

            }
        }
    }

    private fun initViews(){
        tvNoAlarm = findViewById(R.id.tvNoAlarm)
    }

    fun onClickMethod(view: View?) {
        when (view?.id) {
            R.id.backBtn -> backPressed()
            R.id.btnAdd -> {
                val intent=Intent(this, AlarmEditActivity::class.java)
                intent.putExtra("AlarmRequestCode", ALARM_ADD_REQUEST_CODE)
                startActivityForResult(intent,ALARM_ADD_REQUEST_CODE)
            }
        }
    }

    private fun createRecyclerView() {
        recyclerView = findViewById(R.id.alarmRecyclerview)
        mAdapter = AlarmListAdapter()
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.setOnItemClickListener(object : OnAlarmClickListener {
            override fun onItemClick(position: Int) {
                mAlarmEntity=mAdapter.currentList[position]

                val intent=Intent(this@AlarmActivity, AlarmEditActivity::class.java)
                intent.putExtra("AlarmRequestCode", ALARM_EDIT_REQUEST_CODE)
                intent.putExtra("AlarmData",ObjectConverter.fromObjectToString(mAlarmEntity))
                startActivityForResult(intent,ALARM_EDIT_REQUEST_CODE)

            }

            override fun onDeleteClick(position: Int) {
                mAlarmEntity=mAdapter.currentList[position]
                alarmViewModel.deleteAlarm(mAlarmEntity)
            }

            override fun onSwitchClick(position: Int) {
                mAlarmEntity=mAdapter.currentList[position]
                mAlarmEntity.isAlarmOn = !mAlarmEntity.isAlarmOn
                alarmViewModel.updateAlarm(mAlarmEntity)
            }

        })
    }

    override fun onBackPressed() {
        backPressed()
    }

    private fun backPressed() {
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if ( resultCode == Activity.RESULT_OK) {
            if (requestCode== ALARM_ADD_REQUEST_CODE){
                intentData?.getStringExtra("AlarmData")?.let { reply ->
                    val mAlarm = ObjectConverter.fromStringToObject(reply)
                    alarmViewModel.insertAlarm(mAlarm)
                    Log.i("myInformation",mAlarm.alarmTitle)
            }

            }

            if (requestCode== ALARM_EDIT_REQUEST_CODE){
                intentData?.getStringExtra("AlarmData")?.let { reply ->
                    val mAlarm = ObjectConverter.fromStringToObject(reply)
                    alarmViewModel.updateAlarm(mAlarm)
                }

            }
        }
    }


}