package com.nikachapo.potoli

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nikachapo.potoli.task1.Task1Activity
import com.nikachapo.potoli.task2.ui.reminders_list.Task2Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        task1Btn.setOnClickListener {
            startActivity(Intent(this, Task1Activity::class.java))
        }


        task2Btn.setOnClickListener {
            startActivity(Intent(this, Task2Activity::class.java))
        }
    }
}