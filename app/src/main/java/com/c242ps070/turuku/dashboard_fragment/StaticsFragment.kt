package com.c242ps070.turuku.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.c242ps070.turuku.data.local.room.entity.SleepHistoryEntity
import com.c242ps070.turuku.databinding.FragmentStaticsBinding
import com.c242ps070.turuku.utils.formatBedtime
import com.c242ps070.turuku.utils.formatDateRange
import com.c242ps070.turuku.utils.getTodayAndYesterdayDate
import com.c242ps070.turuku.viewmodel.StaticsViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory

class StaticsFragment : Fragment() {
    private var _binding: FragmentStaticsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StaticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaticsBinding.inflate(inflater, container, false)
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[StaticsViewModel::class.java]
        getCurrentDate()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            getHistoryInRange()
        }
    }

    private fun getCurrentDate() {
        val dates = getTodayAndYesterdayDate()
        val today = dates.first
        val yesterday = dates.second
        viewModel.setStartDate(yesterday)
        viewModel.setEndDate(today)
        val formattedDateRange = formatDateRange(yesterday, today)

        binding.tvDateRange.setText(formattedDateRange)
    }

    private fun getHistoryInRange() {
        viewModel.getHistoryInRange().observe(viewLifecycleOwner) { history ->
            val actualBedtime = calculateActualBedtime(history)
            binding.progressActualBedtime.progress = actualBedtime
            binding.tvActualBedtime.text = formatBedtime(actualBedtime)

            val idealBedtime = calculateIdealBedtime(history)
            binding.progressIdealBedtime.progress = idealBedtime
            binding.tvIdealBedtime.text = formatBedtime(idealBedtime)

            binding.sleepAnalysisResult.setText(
                if (actualBedtime < idealBedtime) {
                    "You're not getting enough sleep"
                } else {
                    "Your sleep is good"
                }
            )
        }
    }

    private fun calculateActualBedtime(sleepHistory: List<SleepHistoryEntity>): Int {
        val lastSleepData = sleepHistory.lastOrNull() ?: return 0
        val bedtimeParts = lastSleepData.startTime.split(":").map { it.toInt() }
        val wakeupParts = lastSleepData.endTime.split(":").map { it.toInt() }

        val bedtimeMinutes = bedtimeParts[0] * 60 + bedtimeParts[1]
        val wakeupMinutes = wakeupParts[0] * 60 + wakeupParts[1]
        val sleepDuration = if (wakeupMinutes > bedtimeMinutes) {
            wakeupMinutes - bedtimeMinutes
        } else {
            24 * 60 - bedtimeMinutes + wakeupMinutes
        }

        return sleepDuration
    }

    private fun calculateIdealBedtime(sleepHistory: List<SleepHistoryEntity>): Int {
        val lastSleepData = sleepHistory.lastOrNull() ?: return 0
        val sleepRecParts = lastSleepData.sleepRecommendation?.split(":")?.map { it.toInt() }
        val sleepRecMinutes = (sleepRecParts?.get(0) ?: 0) * 60 + (sleepRecParts?.get(1) ?: 0)

        return sleepRecMinutes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}