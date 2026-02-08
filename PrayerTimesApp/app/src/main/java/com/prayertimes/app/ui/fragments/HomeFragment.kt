package com.prayertimes.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prayertimes.app.databinding.FragmentHomeBinding
import com.prayertimes.app.ui.MainViewModel
import com.prayertimes.app.ui.adapters.EventsAdapter
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var eventsAdapter: EventsAdapter
    
    private val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Set current date
        binding.textCurrentDate.text = dateFormat.format(Date())
        
        // Setup events recycler view
        eventsAdapter = EventsAdapter { event ->
            // Handle event click if needed
        }
        
        binding.recyclerViewEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }
        
        // Setup refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshTodayData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupObservers() {
        // Observe current prayer times
        viewModel.currentPrayerTime.observe(viewLifecycleOwner) { prayerTime ->
            prayerTime?.let {
                binding.textFajrTime.text = it.fajr
                binding.textDhuhrTime.text = it.dhuhr
                binding.textAsrTime.text = it.asr
                binding.textMaghribTime.text = it.maghrib
                binding.textIshaTime.text = it.isha
            }
        }
        
        // Observe next prayer
        viewModel.nextPrayer.observe(viewLifecycleOwner) { nextPrayer ->
            nextPrayer?.let { (name, time) ->
                binding.textNextPrayerName.text = name
                binding.textNextPrayerTime.text = time
                
                // Calculate time remaining
                val timeRemaining = calculateTimeRemaining(time)
                binding.textTimeRemaining.text = timeRemaining
            }
        }
        
        // Observe today's events
        viewModel.todayEvents.observe(viewLifecycleOwner) { events ->
            eventsAdapter.submitList(events)
            binding.textNoEvents.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
        }
        
        // Observe current azan times
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
    
    private fun calculateTimeRemaining(prayerTime: String): String {
        try {
            val currentTime = Calendar.getInstance()
            val prayerCalendar = Calendar.getInstance()
            
            val time = timeFormat.parse(prayerTime)
            time?.let {
                prayerCalendar.time = it
                prayerCalendar.set(Calendar.YEAR, currentTime.get(Calendar.YEAR))
                prayerCalendar.set(Calendar.MONTH, currentTime.get(Calendar.MONTH))
                prayerCalendar.set(Calendar.DAY_OF_MONTH, currentTime.get(Calendar.DAY_OF_MONTH))
                
                // If prayer time has passed today, it's tomorrow's prayer
                if (prayerCalendar.timeInMillis <= currentTime.timeInMillis) {
                    prayerCalendar.add(Calendar.DAY_OF_MONTH, 1)
                }
                
                val diffInMillis = prayerCalendar.timeInMillis - currentTime.timeInMillis
                val hours = diffInMillis / (1000 * 60 * 60)
                val minutes = (diffInMillis % (1000 * 60 * 60)) / (1000 * 60)
                
                return if (hours > 0) {
                    "${hours}h ${minutes}m"
                } else {
                    "${minutes}m"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
