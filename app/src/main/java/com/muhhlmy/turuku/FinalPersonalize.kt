package com.muhhlmy.turuku

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muhhlmy.turuku.databinding.ActivityFinalPersonalizeBinding

class FinalPersonalize : AppCompatActivity() {

    private lateinit var binding: ActivityFinalPersonalizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalPersonalizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            // Do something
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}