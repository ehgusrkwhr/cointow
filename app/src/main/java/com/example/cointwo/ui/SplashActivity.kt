package com.example.cointwo.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cointwo.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySplashBinding
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_DELAY)
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                Log.d("dodo55 ","12이상")
//
//                splashScreen.setOnExitAnimationListener{splashView ->
//                    startActivity(intent)
//                    finish()
//                }
//            } else {
//                Log.d("dodo55 ","12이하")
//                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }


        }


    }

    companion object {
        private const val SPLASH_DELAY: Long = 2000
    }
}