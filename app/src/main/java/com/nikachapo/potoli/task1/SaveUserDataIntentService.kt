package com.nikachapo.potoli.task1

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import java.io.IOException
import java.io.OutputStreamWriter

private const val ACTION_FOO = "FOO"
private const val EXTRA_USER_DATA = "user-data"

class SaveUserDataIntentService : IntentService("SaveUserDataIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_FOO -> {
                val userData = intent.getParcelableExtra<User>(EXTRA_USER_DATA)
                handleActionFoo(userData!!)
            }
        }
    }

    private fun handleActionFoo(userData: User) {

        val gson = Gson()
        val userJsonString = gson.toJson(userData)
        try {
            val outputStreamWriter = OutputStreamWriter(
                openFileOutput(
                    "user.txt",
                    Context.MODE_PRIVATE
                )
            )
            outputStreamWriter.write(userJsonString)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
    }

    companion object {
        @JvmStatic
        fun startSaveUserDataService(context: Context, userData: User) {
            val intent = Intent(context, SaveUserDataIntentService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_USER_DATA, userData)
            }
            context.startService(intent)
        }
    }
}