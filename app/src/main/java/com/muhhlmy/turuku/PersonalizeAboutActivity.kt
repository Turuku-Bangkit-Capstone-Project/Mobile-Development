package com.muhhlmy.turuku

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.muhhlmy.turuku.databinding.ActivityPersonalizeAboutBinding

class PersonalizeAboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalizeAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizeAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner : Spinner = binding.spinnerGender
        val adapter = ArrayAdapter.createFromResource(this, R.array.gender_options,R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter
    }
}