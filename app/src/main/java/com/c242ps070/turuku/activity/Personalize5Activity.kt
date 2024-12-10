package com.c242ps070.turuku.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.c242ps070.turuku.R
import com.c242ps070.turuku.databinding.ActivityPersonalize5Binding

class Personalize5Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalize5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize5Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}