package com.example.android.shissona


import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import androidx.drawerlayout.widget.DrawerLayout

import androidx.fragment.app.Fragment

import androidx.navigation.NavController

import androidx.navigation.NavDestination

import androidx.navigation.findNavController

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title=""
        val navController = this.findNavController(R.id.myNavHost)

        navController.addOnNavigatedListener { nc: NavController, nd: NavDestination ->
            bottom_navigation.setOnNavigationItemSelectedListener {

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