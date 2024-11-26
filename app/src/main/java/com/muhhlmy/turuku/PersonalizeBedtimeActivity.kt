package com.muhhlmy.turuku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.muhhlmy.turuku.databinding.ActivityPersonalizeBedtimeBinding

class PersonalizeBedtimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalizeBedtimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizeBedtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            // Do something
            val intent = Intent(this, PersonalizeWaketimeActivity::class.java)
            startActivity(intent)
        }
    }
}