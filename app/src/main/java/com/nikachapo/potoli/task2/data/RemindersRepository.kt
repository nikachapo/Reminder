package com.nikachapo.potoli.task2.data

import com.nikachapo.potoli.task2.ReminderModel
import com.nikachapo.potoli.task2.db.RemindersDao

class RemindersRepository(private val remindersDao: RemindersDao) {

    val reminders = remindersDao.getReminders()

    suspend fun saveReminder(reminder: ReminderModel) {
        remindersDao.saveReminder(reminder)
    }

    suspend fun setActive(id: Int, isActive: Boolean) {
        remindersDao.setActive(id, if (isActive) 0 else 1)
    }

    suspend fun clear() {
        remindersDao.clear()
    }

    suspend fun delete(reminder: ReminderModel) {
        remindersDao.delete(reminder)
    }

}