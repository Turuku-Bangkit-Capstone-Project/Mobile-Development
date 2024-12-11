package com.c242ps070.turuku.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.R
import com.c242ps070.turuku.databinding.ActivitySetAlarmBinding
import com.c242ps070.turuku.viewmodel.Personalize3ViewModel
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

        viewModel.getUserLoggedIn().observe(this) {
            if (it.wakeupTime != null) {
                viewModel.setWakeupTime(it.wakeupTime)
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
}