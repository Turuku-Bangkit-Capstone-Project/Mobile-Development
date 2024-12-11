package com.c242ps070.turuku.dashboard_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.activity.SetAlarmActivity
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.databinding.FragmentHomeBinding
import com.c242ps070.turuku.utils.convertTimeFormat
import com.c242ps070.turuku.viewmodel.HomeViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var viewModel: HomeViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            checkUserData()
            getSleepRecommendation()

            binding?.sleepButton?.setOnClickListener {
                startActivity(Intent(context, SetAlarmActivity::class.java))
            }
        }
    }

    private fun getSleepRecommendation() {
        viewModel?.getSleepRecommendation()?.observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        binding?.optimalBedtime?.setText(
                            convertTimeFormat(result.data.recommendSleepDuration)
                        )
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkUserData() {
        viewModel?.getUserLoggedIn()?.observe(viewLifecycleOwner) { user ->
            if (user.age == null && user.gender == null && user.chronotype == null) {
                viewModel?.getUserData()?.observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {}
                            is Result.Success -> {
                                if (result.data[0].age != null && result.data[0].gender != null && result.data[0].chronotype != null) {
                                    viewModel?.saveUserData(
                                        age = result.data[0].age!!,
                                        gender = result.data[0].gender.toString(),
                                        chronotype = result.data[0].chronotype!!
                                    )
                                }
                            }
                            is Result.Error -> {
                                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.loading?.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding?.optimalBedtime?.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        viewModel = null
    }
}