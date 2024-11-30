package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c242ps070.turuku.databinding.ActivityPersonalizeBinding

class Personalize1Activity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener{
            startActivity(Intent(this, Personalize2Activity::class.java))
        }
    }
}