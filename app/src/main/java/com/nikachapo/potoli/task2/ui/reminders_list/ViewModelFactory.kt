package com.nikachapo.potoli.task2.ui.reminders_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikachapo.potoli.task2.data.RemindersRepository

class ViewModelFactory(
    private val repository: RemindersRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RemindersViewModel(
                repository, application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}