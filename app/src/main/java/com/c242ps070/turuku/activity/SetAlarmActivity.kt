package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.alarm.Alarm
import com.c242ps070.turuku.databinding.ActivitySetAlarmBinding
import com.c242ps070.turuku.utils.calculateSleepTime
import com.c242ps070.turuku.utils.timeToMillis
import com.c242ps070.turuku.viewmodel.SetAlarmViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class SetAlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetAlarmBinding
    private lateinit var viewModel: SetAlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        viewModel.getLastHistoryRoom().observe(this) { lastHistory ->
            if (lastHistory?.sleepRecommendation != null) {
                val recommendation = lastHistory.sleepRecommendation
                viewModel.setWakeupTime(calculateSleepTime(recommendation))
                binding.tvTime.visibility = View.VISIBLE
            }
        }

        binding.btnPickTime.setOnClickListener {
            openTimePickerDialog()
        }

        viewModel.wakeupTime.observe(this) { wakeupTime ->
            binding.tvTime.text = wakeupTime
            binding.btnPickTime.setText("Change time")
        }

        binding.btnContinue.setOnClickListener {
            setTime()
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[SetAlarmViewModel::class.java]
    }

    private fun openTimePickerDialog() {
        var currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        var currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

        viewModel.wakeupTime.observe(this) {
            currentHour = it.split(":")[0].toInt()
            currentMinute = it.split(":")[1].toInt()
        }

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText("Select alarm time")
                .build()

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)

            viewModel.setWakeupTime(time)
            if (binding.tvTime.visibility == View.GONE) {
                binding.tvTime.visibility = View.VISIBLE
            }
        }

        picker.show(supportFragmentManager, "timePicker")
    }

    private fun setTime() {
        if (viewModel.wakeupTime.value != null) {
            val wakeupTime = viewModel.wakeupTime.value!!
            viewModel.saveAlarmTime(
                bedTime = System.currentTimeMillis(),
                wakeupTime = timeToMillis(wakeupTime)
            )
            Alarm.setAlarm(this, wakeupTime)
            ViewModelFactory.clearInstance()
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}