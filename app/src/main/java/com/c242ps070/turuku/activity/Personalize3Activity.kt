package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c242ps070.turuku.databinding.ActivityPersonalize3Binding

class Personalize3Activity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalize3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            startActivity(Intent(this, Personalize4Activity::class.java))
        }
    }
}