package com.example.tracker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tracker.databinding.ActivityTrackerBinding

class TrackerActivity : AppCompatActivity() {

    private var bind: ActivityTrackerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(bind?.root)
    }
}
