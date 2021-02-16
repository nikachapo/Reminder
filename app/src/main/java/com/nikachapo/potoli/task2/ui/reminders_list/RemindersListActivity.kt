package com.nikachapo.potoli.task2.ui.reminders_list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.nikachapo.potoli.R
import com.nikachapo.potoli.task2.ReminderModel
import com.nikachapo.potoli.task2.data.RemindersRepository
import com.nikachapo.potoli.task2.db.MainDb
import com.nikachapo.potoli.task2.ui.AddReminderFragment
import kotlinx.android.synthetic.main.activity_task2.*

class Task2Activity : AppCompatActivity(),
    AddReminderFragment.AddReminderFragmentListeners {

    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: RemindersViewModel
    private val adapter = RemindersListAdapter {
        if (it.isActive) viewModel.cancelAlarm(it)
        else viewModel.startAlarm(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)

        initList()

        initVM()

        addReminderBtn.setOnClickListener {
            AddReminderFragment().show(supportFragmentManager, "add reminder")
        }

        viewModel.reminders.observe(this, Observer {
            adapter.submitData(it)
        })

    }

    private fun initVM() {
        val db = MainDb.getInstance(this)
        factory = ViewModelFactory(
            RemindersRepository(db.remindersDao),
            application
        )
        viewModel = ViewModelProvider(this, factory).get(RemindersViewModel::class.java)
    }

    private fun initList() {
        remindersList.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                Toast.makeText(this@Task2Activity, "Reminder Deleted", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(remindersList)
    }

    override fun onSaveReminder(reminder: ReminderModel) {
        reminder.isActive = true
        viewModel.saveReminder(reminder)
    }

}