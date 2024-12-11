package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity
import com.c242ps070.turuku.data.remote.request.HistoryRequest
import com.c242ps070.turuku.databinding.ActivityPersonalize5Binding
import com.c242ps070.turuku.viewmodel.Personalize5ViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class Personalize5Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalize5Binding
    private lateinit var viewModel: Personalize5ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        binding.btnContinue.setOnClickListener {
            savePhysicalActivityAndDailySteps()
        }

        viewModel.getUserLoggedIn().observe(this) { user ->
            if (user.physicalActivity != null && user.dailySteps != null) {
                addHistory(user)
            }
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[Personalize5ViewModel::class.java]
    }

    private fun addHistory(user: UserPreferenceModel) {
        val physicalActivity = binding.inputPhysicalActivity.text.toString().toInt()
        val dailySteps = binding.inputDailySteps.text.toString().toInt()
        val request = HistoryRequest(
            user.bedTime!!,
            user.wakeupTime!!,
            user.physicalActivity!!,
            user.dailySteps!!
        )
        viewModel.addHistory(request).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        viewModel.insertSleepHistory(
                            SleepHistoryEntity(
                                startTime = user.bedTime,
                                endTime = user.wakeupTime,
                                physicalActivityLevel = physicalActivity,
                                dailySteps = dailySteps,
                                sleepRecommendation = null
                            )
                        )
                        showLoading(false)
                        startActivity(Intent(this, Personalize6Activity::class.java))
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun savePhysicalActivityAndDailySteps() {
        val physicalActivity = binding.inputPhysicalActivity.text.toString().toInt()
        val dailySteps = binding.inputDailySteps.text.toString().toInt()
        viewModel.savePhysicalActivityAndDailySteps(physicalActivity, dailySteps)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.btnContinue.isEnabled = !isLoading
        binding.btnContinue.setText(if (isLoading) "Please wait.." else "Continue")
    }
}