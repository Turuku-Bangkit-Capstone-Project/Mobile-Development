package com.c242ps070.turuku.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.R
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity
import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.databinding.ActivityLoginBinding
import com.c242ps070.turuku.utils.AppAuthState
import com.c242ps070.turuku.viewmodel.LoginViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        togglePassword()

        //Initiate viewmodel
        initViewModel()

        //Intent to HomeActivity
        binding.getStartedButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        //Intent to SignUpActivity
        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //Login
        binding.getStartedButton.setOnClickListener {
            login()
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun login() {
        val email = binding.inputLoginEmail.text.toString()
        val password = binding.inputLoginPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            val loginRequest = LoginRequest(
                email = email,
                password = password
            )
            viewModel.login(loginRequest).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            val userPreferenceModel = UserPreferenceModel(
                                id = result.data.userId,
                                name = result.data.name,
                                email = email,
                                token = result.data.accessToken
                            )
                            viewModel.saveUserLoggedIn(userPreferenceModel)
                            ViewModelFactory.clearInstance()
                            initViewModel()
                            checkHistory()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkHistory() {
        viewModel.getHistory().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            viewModel.saveAppAuthState(AppAuthState.IS_LOGIN_BUT_NEW_ACCOUNT)
                            val intent = Intent(this, Personalize1Activity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } else {
                            viewModel.saveAppAuthState(AppAuthState.IS_LOGGED_IN)
                            val histories = result.data.map {
                                SleepHistoryEntity(
                                    id = it.id,
                                    startTime = it.bedTime,
                                    endTime = it.wakeupTime,
                                    sleepRecommendation = it.sleepRecommendation,
                                    physicalActivityLevel = it.physicalActivityLevel,
                                    dailySteps = it.dailySteps
                                )
                            }
                            viewModel.insertHistories(histories)

                            ViewModelFactory.clearInstance()
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }
                    is Result.Error -> {
                        if (result.error == "Data history tidak ditemukan") {
                            val intent = Intent(this, Personalize1Activity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                        showLoading(false)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun togglePassword() {
        with(binding.inputLoginPassword) {
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (right - compoundDrawables[2].bounds.width())) {
                        if (transformationMethod == PasswordTransformationMethod.getInstance()) {
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_visibility, 0)
                        } else {
                            transformationMethod = PasswordTransformationMethod.getInstance()
                            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_visibility_off, 0)
                        }
                        return@setOnTouchListener true
                    }
                }
                return@setOnTouchListener false
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.inputLoginEmail.isEnabled = !isLoading
        binding.inputLoginPassword.isEnabled = !isLoading
        binding.getStartedButton.isEnabled = !isLoading
        binding.getStartedButton.setText(if (isLoading) "Signing in..." else "Get Started")
        binding.signUp.isEnabled = !isLoading
    }
}