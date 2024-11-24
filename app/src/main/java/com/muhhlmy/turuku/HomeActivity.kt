package com.muhhlmy.turuku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jummania.AnalogClock
import com.muhhlmy.turuku.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val analogClock: AnalogClock = AnalogClock(this)
    }

}
