package com.nikachapo.potoli.task2.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nikachapo.potoli.task2.ReminderModel

@Dao
interface RemindersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReminder(reminder: ReminderModel)

    @Query("SELECT * FROM reminders_table ORDER BY hour ")
    fun getReminders(): LiveData<List<ReminderModel>>

    @Query("UPDATE reminders_table SET isActive = :isActive WHERE id = :id")
    suspend fun setActive(id: Int, isActive: Int)

    @Query("DELETE FROM reminders_table")
    suspend fun clear()

    @Delete
    suspend fun delete(reminder: ReminderModel)

}