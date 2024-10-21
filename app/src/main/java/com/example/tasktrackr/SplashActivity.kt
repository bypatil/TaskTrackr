package com.example.tasktrackr

import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Delay for 3 seconds and then move to the main activity
        Handler().postDelayed({
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity
        }, 3000) // 3 seconds delay

    }
}