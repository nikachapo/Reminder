package com.nikachapo.potoli.task2

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import com.nikachapo.potoli.task2.data.RemindersRepository
import com.nikachapo.potoli.task2.db.MainDb
import com.nikachapo.potoli.task2.ui.reminders_list.EXTRA_REMINDER_HOUR
import com.nikachapo.potoli.task2.ui.reminders_list.EXTRA_REMINDER_ID
import com.nikachapo.potoli.task2.ui.reminders_list.EXTRA_REMINDER_MINUTE
import com.nikachapo.potoli.task2.ui.reminders_list.EXTRA_REMINDER_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class ReminderService : JobService() {

    private val repository = RemindersRepository(MainDb.getInstance(this).remindersDao)

    override fun onStartJob(params: JobParameters): Boolean {
        val calendar = Calendar.getInstance()
        val extras = params.extras
        val id = extras.getInt(EXTRA_REMINDER_ID)
        val hour = extras.getInt(EXTRA_REMINDER_HOUR)
        val minute = extras.getInt(EXTRA_REMINDER_MINUTE)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        startAlarm(calendar, id, "${hour}:${minute}", extras.getString(EXTRA_REMINDER_NAME)!!)

        GlobalScope.launch(Dispatchers.Main) {
            repository.setActive(id, true)
        }

        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        cancelAlarm()
        return false
    }

    private fun startAlarm(c: Calendar, id: Int, title: String, content: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        intent.putExtra(EXTRA_REMINDER_ID, id)
        intent.putExtra(EXTRA_NOTIFICATION_TITLE, title)
        intent.putExtra(EXTRA_NOTIFICATION_CONTENT, content)

        val pendingIntent =
            PendingIntent.getBroadcast(this, 1, intent, 0)
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)


    }

    private fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}