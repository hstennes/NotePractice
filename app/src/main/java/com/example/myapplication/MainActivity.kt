package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

class MainActivity : AppCompatActivity() {

    val handler: Handler = Handler()

    class LooperThread : Thread("Audio Thread") {

        override fun run() {
            //Perform long running operation.
            Thread.sleep(10000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun nextClicked(view: View) {
        println("press")

        val staffView: StaffView = findViewById(R.id.view_staff)
        staffView.newNote()
    }

}