package com.prayertimes.app.utils

import kotlin.math.*
import java.util.*

object PrayerTimeCalculator {

    fun calculatePrayerTimes(
        date: Date,
        latitude: Double,
        longitude: Double,
        calculationMethod: String = "ISNA"
    ): Map<String, String> {
        
        val calendar = Calendar.getInstance().apply { time = date }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        
        val julianDay = getJulianDay(year, month, day)
        val declinationAngle = getSunDeclination(julianDay)
        val equationOfTime = getEquationOfTime(julianDay)
        
        val times = mutableMapOf<String, String>()
        
        // Get calculation parameters based on method
        val params = getCalculationParameters(calculationMethod)
        
        // Calculate prayer times
        times["Fajr"] = calculateFajr(latitude, longitude, declinationAngle, equationOfTime, params.fajrAngle)
        times["Sunrise"] = calculateSunrise(latitude, longitude, declinationAngle, equationOfTime)
        times["Dhuhr"] = calculateDhuhr(longitude, equationOfTime)
        times["Asr"] = calculateAsr(latitude, longitude, declinationAngle, equationOfTime, params.asrFactor)
        times["Maghrib"] = calculateMaghrib(latitude, longitude, declinationAngle, equationOfTime, params.maghribAngle)
        times["Isha"] = calculateIsha(latitude, longitude, declinationAngle, equationOfTime, params.ishaAngle)
        
        return times
    }
    
    private fun getJulianDay(year: Int, month: Int, day: Int): Double {
        val a = (14 - month) / 12
        val y = year - a
        val m = month + 12 * a - 3
        return day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 + 1721119.0
    }
    
    private fun getSunDeclination(julianDay: Double): Double {
        val n = julianDay - 2451545.0
        val l = (280.460 + 0.9856474 * n) % 360
        val g = Math.toRadians((357.528 + 0.9856003 * n) % 360)
        val lambda = Math.toRadians(l + 1.915 * sin(g) + 0.020 * sin(2 * g))
        return asin(sin(Math.toRadians(23.439)) * sin(lambda))
    }
    
    private fun getEquationOfTime(julianDay: Double): Double {
        val n = julianDay - 2451545.0
        val l = Math.toRadians((280.460 + 0.9856474 * n) % 360)
        val g = Math.toRadians((357.528 + 0.9856003 * n) % 360)
        val lambda = l + Math.toRadians(1.915 * sin(g) + 0.020 * sin(2 * g))
        val alpha = atan2(cos(Math.toRadians(23.439)) * sin(lambda), cos(lambda))
        return 4 * Math.toDegrees(l - alpha)
    }
    
    private fun calculateFajr(lat: Double, lng: Double, declination: Double, eqTime: Double, angle: Double): String {
        val hourAngle = acos((-sin(Math.toRadians(angle)) - sin(Math.toRadians(lat)) * sin(declination)) / 
                            (cos(Math.toRadians(lat)) * cos(declination)))
        val time = 12 - Math.toDegrees(hourAngle) / 15 - lng / 15 + eqTime / 60
        return formatTime(time)
    }
    
    private fun calculateSunrise(lat: Double, lng: Double, declination: Double, eqTime: Double): String {
        val hourAngle = acos((-sin(Math.toRadians(-0.833)) - sin(Math.toRadians(lat)) * sin(declination)) / 
                            (cos(Math.toRadians(lat)) * cos(declination)))
        val time = 12 - Math.toDegrees(hourAngle) / 15 - lng / 15 + eqTime / 60
        return formatTime(time)
    }
    
    private fun calculateDhuhr(lng: Double, eqTime: Double): String {
        val time = 12 - lng / 15 + eqTime / 60
        return formatTime(time)
    }
    
    private fun calculateAsr(lat: Double, lng: Double, declination: Double, eqTime: Double, factor: Double): String {
        val shadowLength = factor + tan(abs(Math.toRadians(lat) - declination))
        val angle = atan(1 / shadowLength)
        val hourAngle = acos((sin(angle) - sin(Math.toRadians(lat)) * sin(declination)) / 
                            (cos(Math.toRadians(lat)) * cos(declination)))
        val time = 12 + Math.toDegrees(hourAngle) / 15 - lng / 15 + eqTime / 60
        return formatTime(time)
    }
    
    private fun calculateMaghrib(lat: Double, lng: Double, declination: Double, eqTime: Double, angle: Double): String {
        val hourAngle = acos((-sin(Math.toRadians(angle)) - sin(Math.toRadians(lat)) * sin(declination)) / 
                            (cos(Math.toRadians(lat)) * cos(declination)))
        val time = 12 + Math.toDegrees(hourAngle) / 15 - lng / 15 + eqTime / 60
        return formatTime(time)
    }
    
    private fun calculateIsha(lat: Double, lng: Double, declination: Double, eqTime: Double, angle: Double): String {
        val hourAngle = acos((-sin(Math.toRadians(angle)) - sin(Math.toRadians(lat)) * sin(declination)) / 
                            (cos(Math.toRadians(lat)) * cos(declination)))
        val time = 12 + Math.toDegrees(hourAngle) / 15 - lng / 15 + eqTime / 60
        return formatTime(time)
    }
    
    private fun formatTime(time: Double): String {
        val hours = time.toInt()
        val minutes = ((time - hours) * 60).toInt()
        return String.format("%02d:%02d", hours, minutes)
    }
    
    private fun getCalculationParameters(method: String): CalculationParams {
        return when (method) {
            "ISNA" -> CalculationParams(15.0, 1.0, -0.833, 15.0) // Islamic Society of North America
            "MWL" -> CalculationParams(18.0, 1.0, -0.833, 17.0)  // Muslim World League
            "EGYPT" -> CalculationParams(19.5, 1.0, -0.833, 17.5) // Egyptian General Authority
            "MAKKAH" -> CalculationParams(18.5, 1.0, -0.833, 90.0) // Umm Al-Qura University, Makkah
            "KARACHI" -> CalculationParams(18.0, 1.0, -0.833, 18.0) // University of Islamic Sciences, Karachi
            "TEHRAN" -> CalculationParams(17.7, 1.0, -0.833, 14.0) // Institute of Geophysics, University of Tehran
            else -> CalculationParams(15.0, 1.0, -0.833, 15.0) // Default to ISNA
        }
    }
    
    private data class CalculationParams(
        val fajrAngle: Double,
        val asrFactor: Double,
        val maghribAngle: Double,
        val ishaAngle: Double
    )
}
