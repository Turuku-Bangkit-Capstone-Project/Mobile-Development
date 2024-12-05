package com.c242ps070.turuku.dashboard_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c242ps070.turuku.activity.ProfileActivity
import com.c242ps070.turuku.activity.ChangepassActivity
import com.c242ps070.turuku.activity.DailyActivity
import com.c242ps070.turuku.databinding.FragmentPreferencesBinding

class PreferencesFragment : Fragment() {
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
//
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProfile.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnDailyActivity.setOnClickListener {
            val intent = Intent(activity, DailyActivity::class.java)
            startActivity(intent)
        }

        binding.btnChangepass.setOnClickListener {
            val intent = Intent(activity, ChangepassActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}