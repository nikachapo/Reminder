package com.nikachapo.potoli.task2.ui.reminders_list

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikachapo.potoli.task2.ReminderModel
import com.nikachapo.potoli.task2.ReminderService
import com.nikachapo.potoli.task2.data.RemindersRepository
import kotlinx.coroutines.launch

const val EXTRA_REMINDER_ID = "reminder_id"
const val EXTRA_REMINDER_NAME = "reminder_name"
const val EXTRA_REMINDER_HOUR = "reminder_hour"
const val EXTRA_REMINDER_MINUTE = "reminder_minute"

class RemindersViewModel(
    private val remindersRepository: RemindersRepository,
    private val application: Application
) : ViewModel() {

    val reminders = remindersRepository.reminders

    fun saveReminder(reminder: ReminderModel) {
        viewModelScope.launch {
            remindersRepository.saveReminder(reminder)
            startAlarm(reminder)
        }
    }

    fun delete(reminder: ReminderModel) {
        viewModelScope.launch {
            remindersRepository.delete(reminder)
        }
    }

    fun startAlarm(reminder: ReminderModel) {
        val componentName = ComponentName(application, ReminderService::class.java)
        val jobInfo = JobInfo.Builder(reminder.id, componentName)
            .setExtras(PersistableBundle().apply {
                putInt(EXTRA_REMINDER_ID, reminder.id)
                putInt(EXTRA_REMINDER_HOUR, reminder.hour)
                putInt(EXTRA_REMINDER_MINUTE, reminder.minute)
                putString(EXTRA_REMINDER_NAME, reminder.name)
            })
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
            .setPersisted(true)
            .setOverrideDeadline(0)
            .build()
        val scheduler = application.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(jobInfo)
    }

    fun cancelAlarm(reminder: ReminderModel) {
        val scheduler = application.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(reminder.id)
    }

    fun clear() {
        viewModelScope.launch {
            remindersRepository.clear()
        }
    }

}