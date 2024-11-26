package com.muhhlmy.turuku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muhhlmy.turuku.databinding.ActivityPersonalizeMessageBinding

class PersonalizeMessageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPersonalizeMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizeMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            // Do something
            startActivity(Intent(this, PersonalizeAboutActivity::class.java))
        }
    }
}