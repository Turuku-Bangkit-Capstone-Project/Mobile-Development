package com.c242ps070.turuku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c242ps070.turuku.dashboard_fragment.HomeFragment
import com.c242ps070.turuku.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add/Replace the Fragment in the FrameLayout
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, fragment) // Replace fragment_container with the fragment
                .commit()
        }
    }
}