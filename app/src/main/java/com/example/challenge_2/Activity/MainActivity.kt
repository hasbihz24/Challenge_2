package com.example.challenge_2.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.challenge_2.R
import com.example.challenge_2.ViewModel.AuthViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
// Menghubungkan BottomNavigationView dengan NavController
        bottomNavigationView.setupWithNavController(navController)
        val authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.getCurrentUser().observe(this) { user ->
            if (user == null) {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}