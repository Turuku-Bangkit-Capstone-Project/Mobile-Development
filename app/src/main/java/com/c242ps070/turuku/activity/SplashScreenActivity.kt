package com.c242ps070.turuku.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.databinding.ActivitySplashScreenBinding
import com.c242ps070.turuku.viewmodel.SplashScreenViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        viewModel.getUserLoggedIn().observe(this) { user ->
            if (user.age != null && user.gender != null && user.bedTime != null && user.wakeupTime != null) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else if (user.token.isNotEmpty()) {
                val intent = Intent(this, Personalize1Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[SplashScreenViewModel::class.java]
    }
}