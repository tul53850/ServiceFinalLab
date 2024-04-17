package edu.temple.servicefinallab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import android.app.Service
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var buttonStartService: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        buttonStartService = findViewById(R.id.buttonStartService)

        buttonStartService.setOnClickListener {
            val input = editText.text.toString().toIntOrNull()
            if (input != null) {
                startCountdownService(input)
            } else {
                // Handle invalid input
            }
        }
    }

    private fun startCountdownService(value: Int) {
        val intent = Intent(this, CountdownService::class.java)
        intent.putExtra("countdown_value", value)
        startService(intent)
    }
}



class CountdownService : Service() {

    private var job: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val value = intent?.getIntExtra("countdown_value", 0) ?: 0
        startCountdown(value)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun startCountdown(value: Int) {
        job = CoroutineScope(Dispatchers.Default).launch {
            for (i in value downTo 0) {
                Log.d("CountdownService", "Countdown: $i")
                delay(1000) // Delay for 1 second
            }
            stopSelf() // Stop the service when countdown finishes
        }
    }
}