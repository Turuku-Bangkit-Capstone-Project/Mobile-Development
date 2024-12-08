package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.databinding.ActivityPersonalize3Binding
import com.c242ps070.turuku.viewmodel.Personalize3ViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale

class Personalize3Activity : AppCompatActivity() {
    private lateinit var binding : ActivityPersonalize3Binding
    private lateinit var viewModel: Personalize3ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        viewModel.getUserLoggedIn().observe(this) {
            if (it.bedTime != null) viewModel.setBedTime(it.bedTime)
        }

        binding.btnPickTime.setOnClickListener {
            openTimePickerDialog()
        }

        viewModel.bedTime.observe(this) {
            binding.tvTime.text = it
        }

        binding.btnContinue.setOnClickListener{
            saveBedTime()
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[Personalize3ViewModel::class.java]
    }

    private fun openTimePickerDialog() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(currentHour)
                .setMinute(currentMinute)
                .setTitleText("Select bed time")
                .build()

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)

            viewModel.setBedTime(time)
            if (binding.tvTime.visibility == View.GONE) {
                binding.tvTime.visibility = View.VISIBLE
            }
        }

        picker.show(supportFragmentManager, "timePicker")
    }

    private fun saveBedTime() {
        if (viewModel.bedTime.value != null) {
            val bedTime = viewModel.bedTime.value!!
            viewModel.saveBedTime(bedTime)
            ViewModelFactory.clearInstance()
            startActivity(Intent(this, Personalize4Activity::class.java))
        }
    }
}