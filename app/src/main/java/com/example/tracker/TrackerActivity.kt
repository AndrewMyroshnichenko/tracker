package com.example.tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tracker.databinding.ActivityTrackerBinding

//Start recreate project with MVI
class TrackerActivity : AppCompatActivity() {

    private var bind: ActivityTrackerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(bind?.root)
    }
}