package com.prayertimes.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.prayertimes.app.R
import com.prayertimes.app.data.models.AzanTime
import com.prayertimes.app.data.models.PrayerTime
import com.prayertimes.app.databinding.FragmentTimesBinding
import com.prayertimes.app.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class TimesFragment : Fragment() {

    private var _binding: FragmentTimesBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Setup prayer time edit buttons
        binding.buttonEditFajr.setOnClickListener { showTimePicker("Fajr", true) }
        binding.buttonEditDhuhr.setOnClickListener { showTimePicker("Dhuhr", true) }
        binding.buttonEditAsr.setOnClickListener { showTimePicker("Asr", true) }
        binding.buttonEditMaghrib.setOnClickListener { showTimePicker("Maghrib", true) }
        binding.buttonEditIsha.setOnClickListener { showTimePicker("Isha", true) }
        
        // Setup azan time edit buttons
        binding.buttonEditFajrAzan.setOnClickListener { showTimePicker("Fajr", false) }
        binding.buttonEditDhuhrAzan.setOnClickListener { showTimePicker("Dhuhr", false) }
        binding.buttonEditAsrAzan.setOnClickListener { showTimePicker("Asr", false) }
        binding.buttonEditMaghribAzan.setOnClickListener { showTimePicker("Maghrib", false) }
        binding.buttonEditIshaAzan.setOnClickListener { showTimePicker("Isha", false) }
    }

    private fun setupObservers() {
        viewModel.currentPrayerTime.observe(viewLifecycleOwner) { prayerTime ->
            prayerTime?.let {
                binding.textFajrTime.text = it.fajr
                binding.textDhuhrTime.text = it.dhuhr
                binding.textAsrTime.text = it.asr
                binding.textMaghribTime.text = it.maghrib
                binding.textIshaTime.text = it.isha
            }
        }
        
        viewModel.currentAzanTime.observe(viewLifecycleOwner) { azanTime ->
            azanTime?.let {
                binding.textFajrAzan.text = it.fajrAzan
                binding.textDhuhrAzan.text = it.dhuhrAzan
                binding.textAsrAzan.text = it.asrAzan
                binding.textMaghribAzan.text = it.maghribAzan
                binding.textIshaAzan.text = it.ishaAzan
            }
        }
    }

    private fun showTimePicker(prayerName: String, isPrayerTime: Boolean) {
        val currentTime = getCurrentTime(prayerName, isPrayerTime)
        val (hour, minute) = parseTime(currentTime)
        
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText(if (isPrayerTime) "Set $prayerName Prayer Time" else "Set $prayerName Azan Time")
            .build()
        
        picker.addOnPositiveButtonClickListener {
            val newTime = String.format("%02d:%02d", picker.hour, picker.minute)
            updateTime(prayerName, newTime, isPrayerTime)
        }
        
        picker.show(parentFragmentManager, "time_picker")
    }
    
    private fun getCurrentTime(prayerName: String, isPrayerTime: Boolean): String {
        return if (isPrayerTime) {
            when (prayerName) {
                "Fajr" -> binding.textFajrTime.text.toString()
                "Dhuhr" -> binding.textDhuhrTime.text.toString()
                "Asr" -> binding.textAsrTime.text.toString()
                "Maghrib" -> binding.textMaghribTime.text.toString()
                "Isha" -> binding.textIshaTime.text.toString()
                else -> "12:00"
            }
        } else {
            when (prayerName) {
                "Fajr" -> binding.textFajrAzan.text.toString()
                "Dhuhr" -> binding.textDhuhrAzan.text.toString()
                "Asr" -> binding.textAsrAzan.text.toString()
                "Maghrib" -> binding.textMaghribAzan.text.toString()
                "Isha" -> binding.textIshaAzan.text.toString()
                else -> "12:00"
            }
        }
    }
    
    private fun parseTime(timeString: String): Pair<Int, Int> {
        return try {
            val parts = timeString.split(":")
            Pair(parts[0].toInt(), parts[1].toInt())
        } catch (e: Exception) {
            Pair(12, 0)
        }
    }
    
    private fun updateTime(prayerName: String, newTime: String, isPrayerTime: Boolean) {
        val today = dateFormat.format(Date())
        
        if (isPrayerTime) {
            val currentPrayerTime = viewModel.currentPrayerTime.value
            val updatedPrayerTime = when (prayerName) {
                "Fajr" -> currentPrayerTime?.copy(fajr = newTime, isManuallySet = true)
                "Dhuhr" -> currentPrayerTime?.copy(dhuhr = newTime, isManuallySet = true)
                "Asr" -> currentPrayerTime?.copy(asr = newTime, isManuallySet = true)
                "Maghrib" -> currentPrayerTime?.copy(maghrib = newTime, isManuallySet = true)
                "Isha" -> currentPrayerTime?.copy(isha = newTime, isManuallySet = true)
                else -> null
            } ?: PrayerTime(
                date = today,
                fajr = if (prayerName == "Fajr") newTime else "05:00",
                dhuhr = if (prayerName == "Dhuhr") newTime else "12:00",
                asr = if (prayerName == "Asr") newTime else "15:30",
                maghrib = if (prayerName == "Maghrib") newTime else "18:00",
                isha = if (prayerName == "Isha") newTime else "19:30",
                isManuallySet = true
            )
            
            viewModel.updatePrayerTime(updatedPrayerTime)
        } else {
            val currentAzanTime = viewModel.currentAzanTime.value
            val updatedAzanTime = when (prayerName) {
                "Fajr" -> currentAzanTime?.copy(fajrAzan = newTime, isManuallySet = true)
                "Dhuhr" -> currentAzanTime?.copy(dhuhrAzan = newTime, isManuallySet = true)
                "Asr" -> currentAzanTime?.copy(asrAzan = newTime, isManuallySet = true)
                "Maghrib" -> currentAzanTime?.copy(maghribAzan = newTime, isManuallySet = true)
                "Isha" -> currentAzanTime?.copy(ishaAzan = newTime, isManuallySet = true)
                else -> null
            } ?: AzanTime(
                date = today,
                fajrAzan = if (prayerName == "Fajr") newTime else "04:55",
                dhuhrAzan = if (prayerName == "Dhuhr") newTime else "11:55",
                asrAzan = if (prayerName == "Asr") newTime else "15:25",
                maghribAzan = if (prayerName == "Maghrib") newTime else "17:55",
                ishaAzan = if (prayerName == "Isha") newTime else "19:25",
                isManuallySet = true
            )
            
            viewModel.updateAzanTime(updatedAzanTime)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
