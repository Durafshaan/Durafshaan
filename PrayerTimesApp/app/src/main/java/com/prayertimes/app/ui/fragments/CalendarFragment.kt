package com.prayertimes.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prayertimes.app.databinding.FragmentCalendarBinding
import com.prayertimes.app.ui.MainViewModel
import com.prayertimes.app.ui.adapters.EventsAdapter
import com.prayertimes.app.data.models.IslamicEvent
import com.prayertimes.app.data.models.EventType
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var eventsAdapter: EventsAdapter
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        setupObservers()
        loadDefaultEvents()
    }

    private fun setupUI() {
        // Setup events recycler view
        eventsAdapter = EventsAdapter { event ->
            showEventDetails(event)
        }
        
        binding.recyclerViewEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }
        
        // Setup calendar view
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val selectedDate = dateFormat.format(calendar.time)
            
            binding.textSelectedDate.text = displayDateFormat.format(calendar.time)
            loadEventsForDate(selectedDate)
        }
        
        // Setup add event button
        binding.fabAddEvent.setOnClickListener {
            showAddEventDialog()
        }
        
        // Set initial date
        val today = Date()
        binding.textSelectedDate.text = displayDateFormat.format(today)
        loadEventsForDate(dateFormat.format(today))
    }

    private fun setupObservers() {
        // Observer will be set up when we load events for specific dates
    }
    
    private fun loadEventsForDate(date: String) {
        // This would typically load events from the database for the specific date
        // For now, we'll show a placeholder
        binding.textNoEvents.visibility = View.VISIBLE
        eventsAdapter.submitList(emptyList())
    }
    
    private fun showEventDetails(event: IslamicEvent) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(event.title)
            .setMessage("${event.description}\n\nType: ${event.eventType.name.replace("_", " ")}")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showAddEventDialog() {
        // This would show a dialog to add a new event
        // For now, we'll add a sample event
        val calendar = Calendar.getInstance()
        val selectedDate = dateFormat.format(calendar.time)
        
        val sampleEvent = IslamicEvent(
            title = "Sample Event",
            description = "This is a sample Islamic event",
            date = selectedDate,
            isImportant = false,
            eventType = EventType.GENERAL,
            isRecurring = false
        )
        
        viewModel.addIslamicEvent(sampleEvent)
    }
    
    private fun loadDefaultEvents() {
        // Load some default Islamic events
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        
        val defaultEvents = listOf(
            IslamicEvent(
                title = "Eid al-Fitr",
                description = "Festival of Breaking the Fast",
                date = "$currentYear-04-21", // Approximate date
                isImportant = true,
                eventType = EventType.EID,
                isRecurring = true
            ),
            IslamicEvent(
                title = "Eid al-Adha",
                description = "Festival of Sacrifice",
                date = "$currentYear-06-28", // Approximate date
                isImportant = true,
                eventType = EventType.EID,
                isRecurring = true
            ),
            IslamicEvent(
                title = "Laylat al-Qadr",
                description = "Night of Power",
                date = "$currentYear-04-17", // Approximate date
                isImportant = true,
                eventType = EventType.LAYLAT_AL_QADR,
                isRecurring = true
            ),
            IslamicEvent(
                title = "Day of Ashura",
                description = "10th day of Muharram",
                date = "$currentYear-08-17", // Approximate date
                isImportant = true,
                eventType = EventType.ASHURA,
                isRecurring = true
            )
        )
        
        defaultEvents.forEach { event ->
            viewModel.addIslamicEvent(event)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
