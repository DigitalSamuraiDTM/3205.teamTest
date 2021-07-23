package com.digitalsamurai.a3205teamtest.splashscreeen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digitalsamurai.a3205teamtest.MainActivity
import com.digitalsamurai.a3205teamtest.R

class ActivitySplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        startActivity(Intent(this, MainActivity::class.java))
        super.onResume()
    }
}