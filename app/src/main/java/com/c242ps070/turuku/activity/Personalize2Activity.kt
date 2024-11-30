package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c242ps070.turuku.databinding.ActivityPersonalize2Binding

class Personalize2Activity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalize2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            startActivity(Intent(this, Personalize3Activity::class.java))
        }
    }
}