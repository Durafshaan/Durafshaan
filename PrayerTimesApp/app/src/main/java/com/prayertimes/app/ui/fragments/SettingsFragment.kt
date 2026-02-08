package com.prayertimes.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.prayertimes.app.databinding.FragmentSettingsBinding
import com.prayertimes.app.ui.MainViewModel
import com.prayertimes.app.data.models.PrayerSettings

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.buttonSaveSettings.setOnClickListener {
            saveSettings()
        }
    }

    private fun setupObservers() {
        viewModel.settings.observe(viewLifecycleOwner) { settings ->
            settings?.let {
                updateUI(it)
            }
        }
    }
    
    private fun updateUI(settings: PrayerSettings) {
        binding.apply {
            switchSilentMode.isChecked = settings.enableSilentMode
            sliderSilentDuration.value = settings.silentModeDuration.toFloat()
            switchNotifications.isChecked = settings.enableNotifications
            switchAzanSound.isChecked = settings.enableAzanSound
            sliderAzanVolume.value = settings.azanVolume
            switchVibration.isChecked = settings.enableVibration
            switchAutoCalculate.isChecked = settings.autoCalculateTimes
            editTextLatitude.setText(settings.latitude.toString())
            editTextLongitude.setText(settings.longitude.toString())
            
            textSilentDurationValue.text = "${settings.silentModeDuration} minutes"
            textAzanVolumeValue.text = "${(settings.azanVolume * 100).toInt()}%"
        }
        
        // Update slider listeners
        binding.sliderSilentDuration.addOnChangeListener { _, value, _ ->
            binding.textSilentDurationValue.text = "${value.toInt()} minutes"
        }
        
        binding.sliderAzanVolume.addOnChangeListener { _, value, _ ->
            binding.textAzanVolumeValue.text = "${(value * 100).toInt()}%"
        }
    }
    
    private fun saveSettings() {
        val settings = PrayerSettings(
            enableSilentMode = binding.switchSilentMode.isChecked,
            silentModeDuration = binding.sliderSilentDuration.value.toInt(),
            enableNotifications = binding.switchNotifications.isChecked,
            enableAzanSound = binding.switchAzanSound.isChecked,
            azanVolume = binding.sliderAzanVolume.value,
            enableVibration = binding.switchVibration.isChecked,
            autoCalculateTimes = binding.switchAutoCalculate.isChecked,
            latitude = binding.editTextLatitude.text.toString().toDoubleOrNull() ?: 0.0,
            longitude = binding.editTextLongitude.text.toString().toDoubleOrNull() ?: 0.0
        )
        
        viewModel.updateSettings(settings)
        
        // Show success message
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            "Settings saved successfully",
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
