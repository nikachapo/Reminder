package com.nikachapo.potoli.task2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders_table")
data class ReminderModel(
    val hour: Int, val minute: Int, val name: String = "alarm", val repeat: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var isActive = true

    fun getTimeString() = "${hour}:${minute}"
}