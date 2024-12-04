package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.data.Result
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
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

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
                            val intent = Intent(this, Personalize1Activity::class.java)
                            startActivity(intent)
                        }
                        is Result.Error -> {
                            //Error
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
        }
    }
}