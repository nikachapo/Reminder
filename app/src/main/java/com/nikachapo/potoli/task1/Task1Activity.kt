package com.nikachapo.potoli.task1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.nikachapo.potoli.R
import kotlinx.android.synthetic.main.activity_task1.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader

class Task1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)

        writeData.setOnClickListener {
            SaveUserDataIntentService.startSaveUserDataService(
                this,
                User("Nika", "Chapo", "nika@gmai", "5353464647", "23fsdfse")
            )
        }

        readData.setOnClickListener {
            readUserData()?.run {
                dataTV.text = toString()
            }
        }

    }

    private fun readUserData(): User? {
        val fis: FileInputStream?
        try {
            fis = openFileInput("user.txt")
        } catch (e: FileNotFoundException) {
            return null
        }
        val isr = InputStreamReader(fis)
        val bufferedReader = BufferedReader(isr)
        val sb = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            sb.append(line)
        }

        val json = sb.toString()
        val gson = Gson()
        return gson.fromJson(json, User::class.java)
    }
}