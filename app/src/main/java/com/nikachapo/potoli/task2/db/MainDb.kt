package com.nikachapo.potoli.task2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nikachapo.potoli.task2.ReminderModel

@Database(entities = [ReminderModel::class], version = 4, exportSchema = false)
abstract class MainDb : RoomDatabase() {

    abstract val remindersDao: RemindersDao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null

        fun getInstance(context: Context): MainDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MainDb::class.java, "main.db")
                .build()
    }
}