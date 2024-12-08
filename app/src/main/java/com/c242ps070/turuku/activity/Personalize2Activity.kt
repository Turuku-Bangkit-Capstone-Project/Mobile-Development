package com.c242ps070.turuku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.R
import com.c242ps070.turuku.databinding.ActivityPersonalize2Binding
import com.c242ps070.turuku.viewmodel.Personalize2ViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class Personalize2Activity : AppCompatActivity() {
    private lateinit var binding : ActivityPersonalize2Binding
    private lateinit var viewModel: Personalize2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalize2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initDropdown()

        viewModel.getUserLoggedIn().observe(this) { user ->
            if (user.age != null && user.gender != null) {
                binding.inputAge.setText(String.format(user.age.toString()))
                binding.inputGender.editText?.setText(user.gender)
            }
        }

        binding.btnContinue.setOnClickListener{
            saveAgeAndGender()
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[Personalize2ViewModel::class.java]
    }

    private fun initDropdown() {
        val gender = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, gender)
        (binding.inputGender.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun saveAgeAndGender() {
        val age = binding.inputAge.text.toString()
        val gender = if (binding.inputGender.editText?.text.toString() == "Male") "1" else "0"

        if (age.isNotEmpty()) {
            viewModel.saveAgeAndGender(age.toInt(), gender)
            startActivity(Intent(this, Personalize3Activity::class.java))
            ViewModelFactory.clearInstance()
        } else {
            binding.inputAge.error = "Age is required"
        }
    }
}