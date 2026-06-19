package com.prayertimes.app.data.database

import androidx.room.*
import androidx.lifecycle.LiveData
import com.prayertimes.app.data.models.IslamicEvent
import com.prayertimes.app.data.models.EventType
import kotlinx.coroutines.flow.Flow

@Dao
interface IslamicEventDao {
    
    @Query("SELECT * FROM islamic_events WHERE date = :date ORDER BY isImportant DESC")
    suspend fun getEventsByDate(date: String): List<IslamicEvent>
    
    @Query("SELECT * FROM islamic_events WHERE date = :date ORDER BY isImportant DESC")
    fun getEventsByDateLiveData(date: String): LiveData<List<IslamicEvent>>
    
    @Query("SELECT * FROM islamic_events ORDER BY date ASC")
    fun getAllEvents(): Flow<List<IslamicEvent>>
    
    @Query("SELECT * FROM islamic_events WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC, isImportant DESC")
    suspend fun getEventsInRange(startDate: String, endDate: String): List<IslamicEvent>
    
    @Query("SELECT * FROM islamic_events WHERE isImportant = 1 ORDER BY date ASC")
    fun getImportantEvents(): Flow<List<IslamicEvent>>
    
    @Query("SELECT * FROM islamic_events WHERE eventType = :eventType ORDER BY date ASC")
    suspend fun getEventsByType(eventType: EventType): List<IslamicEvent>
    
    @Query("SELECT * FROM islamic_events WHERE isRecurring = 1 ORDER BY date ASC")
    suspend fun getRecurringEvents(): List<IslamicEvent>
    
    @Query("SELECT * FROM islamic_events WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY isImportant DESC, date ASC")
    suspend fun searchEvents(query: String): List<IslamicEvent>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: IslamicEvent): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<IslamicEvent>)
    
    @Update
    suspend fun updateEvent(event: IslamicEvent)
    
    @Delete
    suspend fun deleteEvent(event: IslamicEvent)
    
    @Query("DELETE FROM islamic_events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: Long)
    
    @Query("DELETE FROM islamic_events WHERE date < :date AND isRecurring = 0")
    suspend fun deleteOldEvents(date: String)
    
    @Query("SELECT COUNT(*) FROM islamic_events")
    suspend fun getEventsCount(): Int
    
    @Query("SELECT COUNT(*) FROM islamic_events WHERE date = :date")
    suspend fun getEventsCountByDate(date: String): Int
}
