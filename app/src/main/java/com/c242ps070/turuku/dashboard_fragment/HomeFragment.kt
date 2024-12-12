package com.c242ps070.turuku.dashboard_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.R
import com.c242ps070.turuku.activity.SetAlarmActivity
import com.c242ps070.turuku.alarm.Alarm
import com.c242ps070.turuku.data.Result
import com.c242ps070.turuku.databinding.FragmentHomeBinding
import com.c242ps070.turuku.utils.convertTimeFormat
import com.c242ps070.turuku.utils.convertTimeLongToString
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

            viewModel?.getLastHistoryRoom()?.observe(viewLifecycleOwner) { lastHistory ->
                if (lastHistory?.sleepRecommendation != null) {
                    showLoading(false)
                    binding?.optimalBedtime?.setText(
                        convertTimeFormat(lastHistory.sleepRecommendation)
                    )
                } else {
                    getSleepRecommendation(lastHistory?.id!!)
                }
            }

            binding?.sleepButton?.setOnClickListener {
                if (binding?.bedtimeWaketimeContainer?.visibility == View.GONE) {
                    startActivity(Intent(context, SetAlarmActivity::class.java))
                } else {
                    cancelAlarm()
                }
            }

            getAlarmState()
        }
    }

    private fun getSleepRecommendation(historyId: Int) {
        viewModel?.getSleepRecommendation()?.observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        viewModel?.updateSleepRecommendationRoom(
                            historyId, result.data.recommendSleepDuration
                        )
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

    private fun getAlarmState() {
        viewModel?.getAlarmTime()?.observe(viewLifecycleOwner) { alarmTime: Pair<Long?, Long?> ->
            if (alarmTime.first != null && alarmTime.second != null) {
                binding?.bedtimeWaketimeContainer?.visibility = View.VISIBLE
                binding?.ringPrediction?.setText(
                    "Your Alarm will ring at ${convertTimeLongToString(alarmTime.first!!)}"
                )
                binding?.waketime?.text = convertTimeLongToString(alarmTime.first!!)
                binding?.bedtime?.text = convertTimeLongToString(alarmTime.second!!)
                binding?.sleepButtonText?.setText("Wake Up Now")
                binding?.sleepButtonIcon?.setImageResource(R.drawable.ic_alarm)

                binding?.sleepButton?.setOnClickListener {
                    cancelAlarm()
                }
            } else {
                binding?.bedtimeWaketimeContainer?.visibility = View.GONE
                binding?.ringPrediction?.visibility = View.GONE
                binding?.sleepButtonText?.setText("Sleep Now")
                binding?.sleepButtonIcon?.setImageResource(R.drawable.ic_bedtime)

                binding?.sleepButton?.setOnClickListener {
                    startActivity(Intent(context, SetAlarmActivity::class.java))
                }
            }
        }
    }

    private fun cancelAlarm() {
        viewModel?.cancelAlarm()
        Alarm.cancelAlarm(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        viewModel = null
    }
}