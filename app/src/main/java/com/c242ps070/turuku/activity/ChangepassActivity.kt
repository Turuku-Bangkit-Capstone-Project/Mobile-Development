package com.c242ps070.turuku.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.R
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.dataStore
import com.c242ps070.turuku.data.remote.request.ChangePasswordRequest
import com.c242ps070.turuku.data.remote.retrofit.ApiClient
import com.c242ps070.turuku.data.repository.UserRepository
import com.c242ps070.turuku.viewmodel.ChangepassViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class ChangepassActivity : AppCompatActivity() {

    private lateinit var userViewModel: ChangepassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepass)

        val userPreference = UserPreference.getInstance(applicationContext.dataStore)
        val apiService = ApiClient.create(userPreference)
        val userRepository = UserRepository(apiService)
        val factory = ViewModelFactory(userRepository = userRepository)
        userViewModel = ViewModelProvider(this, factory).get(ChangepassViewModel::class.java)

        val btnChangePassword = findViewById<Button>(R.id.signUpButton)
        val etCurrentPassword = findViewById<EditText>(R.id.inputOldPass)
        val etNewPassword = findViewById<EditText>(R.id.inputNewPass)
        val etConfNewPassword = findViewById<EditText>(R.id.inputConfirmPass)

        btnChangePassword.setOnClickListener {
            val currentPassword = etCurrentPassword.text.toString()
            val newPassword = etNewPassword.text.toString()
            val confNewPassword = etConfNewPassword.text.toString()

            if (newPassword == confNewPassword) {
                val request = ChangePasswordRequest(currentPassword, newPassword, confNewPassword)
                userViewModel.changePassword(request).observe(this, Observer { result ->
                    when (result) {
                        is Result.Loading -> {
                            // Show loading indicator
                        }
                        is Result.Success -> {
                            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}