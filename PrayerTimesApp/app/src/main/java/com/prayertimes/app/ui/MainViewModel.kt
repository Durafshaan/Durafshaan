package com.prayertimes.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prayertimes.app.PrayerTimesApplication
import com.prayertimes.app.data.models.PrayerSettings
import com.prayertimes.app.data.models.PrayerTime
import com.prayertimes.app.data.models.AzanTime
import com.prayertimes.app.data.models.IslamicEvent
import com.prayertimes.app.utils.PrayerTimeCalculator
import com.prayertimes.app.utils.SilentModeManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = (application as PrayerTimesApplication).database
    private val prayerTimeDao = database.prayerTimeDao()
    private val azanTimeDao = database.azanTimeDao()
    private val islamicEventDao = database.islamicEventDao()
    private val settingsDao = database.settingsDao()

    private val _currentPrayerTime = MutableLiveData<PrayerTime?>()
    val currentPrayerTime: LiveData<PrayerTime?> = _currentPrayerTime

    private val _currentAzanTime = MutableLiveData<AzanTime?>()
    val currentAzanTime: LiveData<AzanTime?> = _currentAzanTime

    private val _todayEvents = MutableLiveData<List<IslamicEvent>>()
    val todayEvents: LiveData<List<IslamicEvent>> = _todayEvents

    private val _settings = MutableLiveData<PrayerSettings?>()
    val settings: LiveData<PrayerSettings?> = _settings

    private val _nextPrayer = MutableLiveData<Pair<String, String>?>()
    val nextPrayer: LiveData<Pair<String, String>?> = _nextPrayer

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    init {
        loadTodayData()
        loadSettings()
    }

    private fun loadTodayData() {
        val today = dateFormat.format(Date())
        
        viewModelScope.launch {
            // Load prayer times
            val prayerTime = prayerTimeDao.getPrayerTimeByDate(today)
            _currentPrayerTime.value = prayerTime

            // Load azan times
            val azanTime = azanTimeDao.getAzanTimeByDate(today)
            _currentAzanTime.value = azanTime

            // Load today's events
            val events = islamicEventDao.getEventsByDate(today)
            _todayEvents.value = events

            // Calculate next prayer
            prayerTime?.let { calculateNextPrayer(it) }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val settings = settingsDao.getSettings() ?: PrayerSettings()
            _settings.value = settings
            settingsDao.insertSettings(settings)
        }
    }

    private fun calculateNextPrayer(prayerTime: PrayerTime) {
        val currentTime = timeFormat.format(Date())
        val prayers = listOf(
            "Fajr" to prayerTime.fajr,
            "Dhuhr" to prayerTime.dhuhr,
            "Asr" to prayerTime.asr,
            "Maghrib" to prayerTime.maghrib,
            "Isha" to prayerTime.isha
        )

        for ((name, time) in prayers) {
            if (time > currentTime) {
                _nextPrayer.value = name to time
                return
            }
        }

        // If no prayer is left today, next prayer is tomorrow's Fajr
        viewModelScope.launch {
            val tomorrow = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 1)
            }
            val tomorrowDate = dateFormat.format(tomorrow.time)
            val tomorrowPrayer = prayerTimeDao.getPrayerTimeByDate(tomorrowDate)
            tomorrowPrayer?.let {
                _nextPrayer.value = "Fajr" to it.fajr
            }
        }
    }

    fun updatePrayerTime(prayerTime: PrayerTime) {
        viewModelScope.launch {
            prayerTimeDao.insertPrayerTime(prayerTime)
            _currentPrayerTime.value = prayerTime
            calculateNextPrayer(prayerTime)
            
            // Schedule silent mode if enabled
            _settings.value?.let { settings ->
                if (settings.enableSilentMode) {
                    SilentModeManager.scheduleAllSilentModes(getApplication(), prayerTime, settings)
                }
            }
        }
    }

    fun updateAzanTime(azanTime: AzanTime) {
        viewModelScope.launch {
            azanTimeDao.insertAzanTime(azanTime)
            _currentAzanTime.value = azanTime
        }
    }

    fun addIslamicEvent(event: IslamicEvent) {
        viewModelScope.launch {
            islamicEventDao.insertEvent(event)
            if (event.date == dateFormat.format(Date())) {
                loadTodayData()
            }
        }
    }

    fun updateSettings(settings: PrayerSettings) {
        viewModelScope.launch {
            settingsDao.updateSettings(settings)
            _settings.value = settings
        }
    }

    fun generatePrayerTimesForMonth(year: Int, month: Int) {
        viewModelScope.launch {
            val settings = _settings.value ?: return@launch
            if (!settings.autoCalculateTimes || settings.latitude == 0.0 || settings.longitude == 0.0) {
                return@launch
            }

            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, 1)
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            val prayerTimes = mutableListOf<PrayerTime>()

            for (day in 1..daysInMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, day)
                val date = dateFormat.format(calendar.time)

                // Check if prayer time already exists
                val existingPrayerTime = prayerTimeDao.getPrayerTimeByDate(date)
                if (existingPrayerTime == null) {
                    val calculatedTimes = PrayerTimeCalculator.calculatePrayerTimes(
                        calendar.time,
                        settings.latitude,
                        settings.longitude,
                        settings.calculationMethod
                    )

                    val prayerTime = PrayerTime(
                        date = date,
                        fajr = calculatedTimes["Fajr"] ?: "05:00",
                        dhuhr = calculatedTimes["Dhuhr"] ?: "12:00",
                        asr = calculatedTimes["Asr"] ?: "15:30",
                        maghrib = calculatedTimes["Maghrib"] ?: "18:00",
                        isha = calculatedTimes["Isha"] ?: "19:30",
                        isManuallySet = false
                    )

                    prayerTimes.add(prayerTime)
                }
            }

            if (prayerTimes.isNotEmpty()) {
                prayerTimeDao.insertPrayerTimes(prayerTimes)
                loadTodayData()
            }
        }
    }

    fun refreshTodayData() {
        loadTodayData()
    }
}
