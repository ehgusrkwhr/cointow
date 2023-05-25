package com.example.cointwo.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.cointwo.databinding.ActivityMainBinding
import com.example.cointwo.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingFragment()

    }

    private fun settingFragment() {
        supportFragmentManager.apply {
            val trans = beginTransaction()
            trans.replace(binding.container.id, HomeFragment())
            trans.commit()
        }
    }

}