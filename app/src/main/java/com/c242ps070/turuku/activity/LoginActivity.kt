package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreferenceModel
import com.c242ps070.turuku.data.remote.request.LoginRequest
import com.c242ps070.turuku.databinding.ActivityLoginBinding
import com.c242ps070.turuku.viewmodel.LoginViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        is Result.Loading -> {
                            //Loading
                        }
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
                            refreshToken()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshToken() {
        viewModel.refreshToken().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        val userPreferenceModel = UserPreferenceModel(
                            token = result.data.accessToken
                        )
                        viewModel.saveUserLoggedIn(userPreferenceModel)
                        ViewModelFactory.clearInstance()
                        showLoading(false)

                        val intent = Intent(this, Personalize1Activity::class.java)
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.inputLoginEmail.isEnabled = !isLoading
        binding.inputLoginPassword.isEnabled = !isLoading
        binding.getStartedButton.isEnabled = !isLoading
        binding.getStartedButton.text = if (isLoading) "Signing in..." else "Get Started"
        binding.signUp.isEnabled = !isLoading
    }
}