package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.request.UpsertUserDataRequest
import com.c242ps070.turuku.databinding.ActivityPersonalize6Binding
import com.c242ps070.turuku.utils.getChronotypeName
import com.c242ps070.turuku.viewmodel.Personalize6ViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class Personalize6Activity : AppCompatActivity() {
    private lateinit var binding : ActivityPersonalize6Binding
    private lateinit var viewModel: Personalize6ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        getChronotype()

        binding.btnContinueToHome.setOnClickListener {
            ViewModelFactory.clearInstance()
            val intent = Intent(this@Personalize6Activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[Personalize6ViewModel::class.java]
    }

    private fun getChronotype() {
        viewModel.getUserLoggedIn().observe(this) { user ->
            if (user.bedTime != null && user.wakeupTime != null) {
                viewModel.getChronotype().observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                viewModel.saveChronotype(result.data.chronotype)

                                val userData = UpsertUserDataRequest(
                                    age = user.age,
                                    gender = if (user.gender == "Male") "1" else "0",
                                    chronotype = result.data.chronotype
                                )
                                insertUserData(userData, getChronotypeName(result.data.chronotype))
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun insertUserData(userDataRequest: UpsertUserDataRequest, chronotype: String) {
        viewModel.insertUserData(userDataRequest).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        binding.chronotypeResult.visibility = View.VISIBLE
                        binding.chronotypeResult.setText(chronotype)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnContinueToHome.isEnabled = !isLoading
    }
}