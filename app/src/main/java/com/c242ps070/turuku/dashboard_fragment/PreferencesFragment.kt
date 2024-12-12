package com.c242ps070.turuku.dashboard_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.c242ps070.turuku.activity.ChangepassActivity
import com.c242ps070.turuku.activity.LoginActivity
import com.c242ps070.turuku.activity.ProfileActivity
import com.c242ps070.turuku.activity.Personalize5Activity
import com.c242ps070.turuku.data.local.datastore.UserPreference
import com.c242ps070.turuku.data.local.datastore.dataStore
import com.c242ps070.turuku.databinding.FragmentPreferencesBinding
import com.c242ps070.turuku.utils.AppAuthState
import com.c242ps070.turuku.viewmodel.PreferencesViewModel
import com.c242ps070.turuku.viewmodel.factory.ViewModelFactory
import kotlinx.coroutines.launch

class PreferencesFragment : Fragment() {
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PreferencesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[PreferencesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnProfile.setOnClickListener {
//            val intent = Intent(activity, ProfileActivity::class.java)
//            startActivity(intent)
//        }

        binding.btnDailyActivity.setOnClickListener {
            val intent = Intent(activity, Personalize5Activity::class.java)
            startActivity(intent)
        }

        binding.btnChangepass.setOnClickListener {
            val intent = Intent(activity, ChangepassActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            viewModel.logout()
            viewModel.clearLocalData()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}