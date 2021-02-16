package com.nikachapo.potoli.task2.ui.reminders_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikachapo.potoli.R
import com.nikachapo.potoli.task2.ReminderModel
import kotlinx.android.synthetic.main.item_reminder.view.*

class RemindersListAdapter(private val onReminderStatusChanged: (ReminderModel) -> Unit) :
    RecyclerView.Adapter<RemindersListAdapter.ReminderViewHolder>() {

    private val reminders = mutableListOf<ReminderModel>()

    fun submitData(reminders: List<ReminderModel>) {
        this.reminders.clear()
        this.reminders.addAll(reminders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        )
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(reminders[position])
    }

    fun getItemAt(adapterPosition: Int): ReminderModel = reminders[adapterPosition]

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(reminder: ReminderModel) {
            itemView.itemTimeTV.text = "${reminder.hour}:${reminder.minute}"
            itemView.itemDescTV.text = reminder.name
            setAlarmImage(reminder)

            itemView.alarmIV.setOnClickListener {
                reminder.isActive = !reminder.isActive
                setAlarmImage(reminder)
                onReminderStatusChanged(reminder)
            }
        }

        private fun setAlarmImage(reminder: ReminderModel) {
            val resource =
                if (reminder.isActive) R.drawable.ic_alarm_active else R.drawable.ic_alarm_inactive
            itemView.alarmIV.setImageResource(resource)
        }
    }
}