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
import com.c242ps070.turuku.R
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.remote.request.RegisterRequest
import com.c242ps070.turuku.databinding.ActivitySignUpBinding
import com.c242ps070.turuku.viewmodel.SignUpViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initiate viewmodel
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]

        // Go to Personalize1Activity
        binding.signUpButton.setOnClickListener{
            signUp()
        }

        togglePassword()
        toggleConfirmPassword()
    }

    private fun signUp() {
        val name = binding.inputSignUpName.text.toString()
        val email = binding.inputSignUpEmail.text.toString()
        val password = binding.inputSignUpPassword.text.toString()
        val confirmPassword = binding.inputSignUpConfirmPassword.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty()
            && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            val registerRequest = RegisterRequest(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
            viewModel.signUp(registerRequest).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(this@SignUpActivity, result.data.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        is Result.Error -> {
                            Toast.makeText(this@SignUpActivity, result.error, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this@SignUpActivity, "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.inputSignUpName.isEnabled = !isLoading
        binding.inputSignUpEmail.isEnabled = !isLoading
        binding.inputSignUpPassword.isEnabled = !isLoading
        binding.inputSignUpConfirmPassword.isEnabled = !isLoading
        binding.signUpButton.text = if (isLoading) "Signing up..." else "Sign Up"
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun togglePassword() {
        with(binding.inputSignUpPassword) {
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

    @SuppressLint("ClickableViewAccessibility")
    private fun toggleConfirmPassword() {
        with(binding.inputSignUpConfirmPassword) {
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
}