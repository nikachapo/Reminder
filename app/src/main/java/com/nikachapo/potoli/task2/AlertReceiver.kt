package com.nikachapo.potoli.task2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.nikachapo.potoli.task2.data.RemindersRepository
import com.nikachapo.potoli.task2.db.MainDb
import com.nikachapo.potoli.task2.ui.reminders_list.EXTRA_REMINDER_ID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val EXTRA_NOTIFICATION_TITLE = "n_title"
const val EXTRA_NOTIFICATION_CONTENT = "n_content"

class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val id = intent.getIntExtra(EXTRA_REMINDER_ID, -1)
        val title = intent.getStringExtra(EXTRA_NOTIFICATION_TITLE)!!
        val content = intent.getStringExtra(EXTRA_NOTIFICATION_CONTENT)!!

        val repository = RemindersRepository(MainDb.getInstance(context).remindersDao)

        GlobalScope.launch {
            repository.setActive(id, false)
        }

        val notificationHelper = NotificationHelper(context)
        val nb: NotificationCompat.Builder =
            notificationHelper.getChannelNotification(title, content)
        notificationHelper.manager.notify(1, nb.build())
    }

}