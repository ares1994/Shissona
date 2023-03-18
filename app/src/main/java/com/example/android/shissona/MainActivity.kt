package com.example.android.shissona


import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.LayoutInflater

import androidx.navigation.NavController

import androidx.navigation.NavDestination

import androidx.navigation.findNavController
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

        navController.addOnDestinationChangedListener { _: NavController, nd: NavDestination, _ ->
            binding.bottomNavigation.setOnNavigationItemSelectedListener {

                if (nd.id == R.id.homeFragment) {

                    if (it.itemId == R.id.toCharts) {

                        navController.navigate(R.id.action_homeFragment_to_dataFragment)

                    } else if (it.itemId == R.id.toAdd) {

                        return@setOnNavigationItemSelectedListener false

                    }

                } else if (nd.id == R.id.dataFragment) {

                    if (it.itemId == R.id.toAdd) {

                        navController.navigate(R.id.action_dataFragment_to_homeFragment)

                    } else if (it.itemId == R.id.toCharts) {

                        return@setOnNavigationItemSelectedListener false
                    }
                }
                return@setOnNavigationItemSelectedListener true
            }
        }
    }





}