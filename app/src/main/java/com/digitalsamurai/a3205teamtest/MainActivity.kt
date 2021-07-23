package com.digitalsamurai.a3205teamtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navBar : BottomNavigationView = findViewById(R.id.act_main_bottomNavBar);

        val navController = findNavController(R.id.act_main_navHost)

        val appBar = AppBarConfiguration(
            setOf(R.id.navigation_search, R.id.navigation_downloads)

        )

        setupActionBarWithNavController(navController, appBar)
        navBar.setupWithNavController(navController)
    }
}