package com.muhhlmy.turuku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhhlmy.turuku.databinding.ActivityPersonalizeWaketimeBinding

class PersonalizeWaketime : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalizeWaketimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizeWaketimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            // Do something
            val intent = Intent(this, PersonalizeLifestyle::class.java)
            startActivity(intent)
        }
    }
}