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
            val intent = Intent(this, CountdownService::class.java)
            intent.putExtra("countdown", editText.text.toString().toInt())
            startService(intent)
        }
    }
}



class CountdownService : Service() {



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.run{
            val from = getIntExtra("countdown", 10)
            CoroutineScope(Dispatchers.IO).launch{
                repeat(from){
                    Log.d("countdown", (10-it).toString())
                    delay(1000)
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}