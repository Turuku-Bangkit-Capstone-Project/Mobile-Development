package com.muhhlmy.turuku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhhlmy.turuku.databinding.ActivityPersonalizeLifestyleBinding

class PersonalizeLifestyle : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalizeLifestyleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizeLifestyleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            // Do something
            val intent = Intent(this, FinalPersonalize::class.java)
            startActivity(intent)
        }
    }
}