package com.orbital.sonic.roomdatabasemvvm.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orbital.sonic.roomdatabasemvvm.R
import com.orbital.sonic.roomdatabasemvvm.adapter.RepeatAlarmAdapter
import com.orbital.sonic.roomdatabasemvvm.datamodel.AlarmEntity
import com.orbital.sonic.roomdatabasemvvm.datamodel.RepeatAlarmItem
import com.orbital.sonic.roomdatabasemvvm.interfaces.OnItemClickListener
import com.orbital.sonic.roomdatabasemvvm.utils.ObjectConverter
import kotlin.collections.ArrayList

class AlarmEditActivity : BaseActivity() {

    private lateinit var mLayoutManagerRepeat: RecyclerView.LayoutManager
    private lateinit var mRecyclerViewRepeat: RecyclerView
    private lateinit var mAdapterRepeatAlarm: RepeatAlarmAdapter
    private lateinit var mDaysList: ArrayList<RepeatAlarmItem>
    private val myDays = arrayOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    private lateinit var alarmTimePicker: TimePicker
    private var repeatOnOffCheck = false
    private lateinit var repeatOnOffImage: ImageView

    private val alarmAddRequestCode = 1
    private val alarmEditRequestCode = 2
    private var requestCode = -1
    private lateinit var alarmEntity: AlarmEntity

    var alarmHour: Int = 12
    var alarmMinute: Int = 12
    var amPm: String = "AM"

    private lateinit var alarmNameEdit: TextView
    private lateinit var alarmToneEdit: TextView
    private lateinit var vibrationSwitch: SwitchCompat
    private lateinit var flashLightSwitch: SwitchCompat
    private lateinit var flashLightBlinkingSwitch: SwitchCompat
    private lateinit var snoozeSwitch: SwitchCompat
    private lateinit var radioFadeIn: RadioButton
    private lateinit var radioFadeOut: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_edit)

        requestCode = intent.getIntExtra("AlarmRequestCode", 0)
        if (requestCode == alarmEditRequestCode) {
            alarmEntity = ObjectConverter.fromStringToObject(intent.getStringExtra("AlarmData")!!)

        }

        initViews()
        buildRepeatList()
        buildClockRecyclerView()

        alarmTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->

            alarmHour = hourOfDay
            alarmMinute = minute
            amPm = if (hourOfDay < 12) {
                "AM"
            } else {
                "PM"
            }
            Log.i("myInformation", hourOfDay.toString())
            Log.i("myInformation", minute.toString())
        }


    }

    private fun initViews() {
        mRecyclerViewRepeat = findViewById(R.id.recyclerViewsDays)
        alarmTimePicker = findViewById(R.id.alarmTimePicker)
        repeatOnOffImage = findViewById(R.id.repeatOnOffImage)
        alarmNameEdit = findViewById(R.id.alarmNameEdit)
        alarmToneEdit = findViewById(R.id.alarmToneEdit)
        vibrationSwitch = findViewById(R.id.vibrationSwitch)
        flashLightSwitch = findViewById(R.id.flashLightSwitch)
        flashLightBlinkingSwitch = findViewById(R.id.flashLightBlinkingSwitch)
        snoozeSwitch = findViewById(R.id.snoozeSwitch)
        radioFadeIn = findViewById(R.id.radio_fadein)
        radioFadeOut = findViewById(R.id.radio_fadeout)

        if (requestCode == alarmEditRequestCode) {
            repeatOnOffCheck = alarmEntity.isRepeatAlarm
            if (repeatOnOffCheck) {
                mRecyclerViewRepeat.visibility = View.VISIBLE
                repeatOnOffImage.setImageResource(R.drawable.ic_repeat_on)

            } else {
                mRecyclerViewRepeat.visibility = View.GONE
                repeatOnOffImage.setImageResource(R.drawable.ic_repeat_off)
            }
            alarmNameEdit.text = alarmEntity.alarmTitle
            alarmToneEdit.text = alarmEntity.alarmTone
            vibrationSwitch.isChecked=alarmEntity.isVibrationAlarm
            if (alarmEntity.isFadeIn){
                radioFadeIn.isChecked=true
                radioFadeOut.isChecked=false
            }else{
                radioFadeIn.isChecked=false
                radioFadeOut.isChecked=true
            }
            flashLightSwitch.isChecked=alarmEntity.isFlashLightOn
            flashLightBlinkingSwitch.isChecked=alarmEntity.isFlashLightBlinking
            snoozeSwitch.isChecked=alarmEntity.isSnooze



        }
    }

    private fun buildClockRecyclerView() {
        mLayoutManagerRepeat = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerViewRepeat.layoutManager = mLayoutManagerRepeat
        mAdapterRepeatAlarm = RepeatAlarmAdapter()
        mRecyclerViewRepeat.adapter = mAdapterRepeatAlarm
        mAdapterRepeatAlarm.setAlarmList(mDaysList)

        mAdapterRepeatAlarm.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                mDaysList[position].dayState = !mDaysList[position].dayState

                mAdapterRepeatAlarm.notifyDataSetChanged()

            }

        })

    }

    private fun buildRepeatList() {
        mDaysList = ArrayList()

        if (requestCode==alarmEditRequestCode){
            mDaysList= ObjectConverter.fromStringToList(alarmEntity.repeatDays)
        }else{
            for (index in myDays.indices) {
                mDaysList.add(RepeatAlarmItem(myDays[index], false))
            }
        }

    }

    fun onClickMethod(view: View?) {
        when (view?.id) {
            R.id.backBtn -> backPressed()
            R.id.btnSaveAlarm -> {
                if (requestCode==alarmAddRequestCode){
                    alarmEntity = AlarmEntity(
                        alarmTitle = alarmNameEdit.text.toString(),
                        isAlarmOn = true,
                        alarmHour = alarmHour,
                        alarmMinute = alarmMinute,
                        amPm = amPm,
                        isRepeatAlarm = repeatOnOffCheck,
                        repeatDays = ObjectConverter.fromListToString(mDaysList),
                        isVibrationAlarm = vibrationSwitch.isChecked,
                        alarmTone = alarmToneEdit.text.toString(),
                        isFadeIn = radioFadeIn.isChecked,
                        isFlashLightOn = flashLightSwitch.isChecked,
                        isFlashLightBlinking = flashLightBlinkingSwitch.isChecked,
                        isSnooze = snoozeSwitch.isChecked
                    )
                }else {

                        alarmEntity.alarmTitle = alarmNameEdit.text.toString()
                        alarmEntity.isAlarmOn = true
                        alarmEntity.alarmHour = alarmHour
                        alarmEntity.alarmMinute = alarmMinute
                        alarmEntity.amPm = amPm
                        alarmEntity.isRepeatAlarm = repeatOnOffCheck
                        alarmEntity.repeatDays = ObjectConverter.fromListToString(mDaysList)
                        alarmEntity.isVibrationAlarm = vibrationSwitch.isChecked
                        alarmEntity.alarmTone = alarmToneEdit.text.toString()
                        alarmEntity.isFadeIn = radioFadeIn.isChecked
                        alarmEntity.isFlashLightOn = flashLightSwitch.isChecked
                        alarmEntity.isFlashLightBlinking = flashLightBlinkingSwitch.isChecked
                        alarmEntity.isSnooze = snoozeSwitch.isChecked

                }


                val replyIntent = Intent()
                replyIntent.putExtra(
                    "AlarmData",
                    ObjectConverter.fromObjectToString(alarmEntity)
                )
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
            R.id.btnRepeatOnOff -> {
                if (repeatOnOffCheck) {
                    mRecyclerViewRepeat.visibility = View.GONE
                    repeatOnOffCheck = false
                    repeatOnOffImage.setImageResource(R.drawable.ic_repeat_off)
                } else {
                    mRecyclerViewRepeat.visibility = View.VISIBLE
                    repeatOnOffCheck = true
                    repeatOnOffImage.setImageResource(R.drawable.ic_repeat_on)
                }
            }
            R.id.btnAlarmName -> showNameDialog()
            R.id.btnAlarmTone -> showMessage("Dummy Alarm Tone")
        }

        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_fadein ->
                    if (checked) {
                        showMessage("Fade In")
                    }
                R.id.radio_fadeout ->
                    if (checked) {
                        showMessage("Fade OUt")
                    }

            }
        }

        if (view is SwitchCompat) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.vibrationSwitch ->
                    if (checked) {
                        showMessage("Vibration On")
                    } else {
                        showMessage("Vibration Off")
                    }
                R.id.flashLightBlinkingSwitch -> if (checked) {
                    showMessage("Flash Light Blinking On")
                } else {
                    showMessage("Flash Light Blinking Off")
                }

                R.id.flashLightSwitch -> if (checked) {
                    showMessage("Flash Light On")
                } else {
                    showMessage("Flash Light Off")
                }

                R.id.snoozeSwitch -> {
                    if (checked) {
                        showMessage("Snooze Enabled")
                    } else {
                        showMessage("Snooze Disabled")
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        backPressed()
    }

    private fun backPressed() {
        super.onBackPressed()
    }

    private fun showNameDialog() {
        val mDialog = Dialog(this)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setCanceledOnTouchOutside(true)
        mDialog.setContentView(R.layout.dialog_name)

        val alarmTitleNameText = mDialog.findViewById<EditText>(R.id.alarmTitleNameText)

        val mCancel = mDialog.findViewById<TextView>(R.id._cancel)
        mCancel.setOnClickListener {
            mDialog.dismiss()
        }

        val mDone = mDialog.findViewById<TextView>(R.id._done)
        mDone.setOnClickListener {
            alarmNameEdit.text = alarmTitleNameText.text.toString()
            mDialog.dismiss()
        }

        mDialog.show()
    }
}