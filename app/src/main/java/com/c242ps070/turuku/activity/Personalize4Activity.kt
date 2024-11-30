package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c242ps070.turuku.databinding.ActivityPersonalize4Binding

class Personalize4Activity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalize4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            startActivity(Intent(this, Personalize5Activity::class.java))
        }

    }
}