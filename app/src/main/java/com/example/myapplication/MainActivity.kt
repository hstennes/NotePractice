package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var tuner: TunerEngine? = null

    private val handler: Handler = Handler(Looper.getMainLooper())

    //From C6 to A3
    val noteFreqs = intArrayOf(1046, 988, 880, 784, 698, 659, 587, 523, 494, 440, 392, 349, 330, 294, 262, 247, 220)

    val noteFreqs2 = intArrayOf(2102, 1985, 1760, 1570, 1405, 1320, 1175, 1045, 987, 882, 783, 700, 660, 585, 530, 495, 438)

    val TOLERANCE = 10

    val CONSECUTIVE = 2

    var correctSamples = 0

    private val callback = Runnable {
        tuner?.let {
            val freq = it.currentFrequency
            val staffView: StaffView = findViewById(R.id.view_staff)
            val correctFreq = noteFreqs2[staffView.note]

            val textFreq: TextView = findViewById(R.id.text_freq)
            textFreq.setText("Frequency: " + freq)

            if(freq >= correctFreq - TOLERANCE && freq <= correctFreq + TOLERANCE) {
                correctSamples++
                if(correctSamples >= CONSECUTIVE) {
                    correctSamples = 0
                    staffView.newNote()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tuner = TunerEngine(handler, callback, this)
        tuner!!.start();

        println("Path thing: " + System.getProperty("java.library.path"))
    }

    fun nextClicked(view: View) {
        val staffView: StaffView = findViewById(R.id.view_staff)
        staffView.newNote()
    }

}