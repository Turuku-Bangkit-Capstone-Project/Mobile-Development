package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c242ps070.turuku.HomeActivity
import com.c242ps070.turuku.databinding.ActivityPersonalize5Binding

class Personalize5Activity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalize5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinueToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}