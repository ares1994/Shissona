package com.example.android.shissona


import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.LayoutInflater

import androidx.navigation.NavController

import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination

import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.android.shissona.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))


        setContentView(binding.root)
        title=""
        val navController = this.findNavController(R.id.myNavHost)

        binding.bottomNavigation.setOnItemSelectedListener {
            navigateTo(it.itemId, navController)
            return@setOnItemSelectedListener true
        }
    }


    private fun navigateTo(destinationId: Int, navController: NavController) {
        val shouldSaveAndRestoreState = navController.currentDestination?.id != destinationId
        navController.navigate(destinationId, null, navOptions {
            launchSingleTop = true
            restoreState = shouldSaveAndRestoreState
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = shouldSaveAndRestoreState
            }
        })

    }


}