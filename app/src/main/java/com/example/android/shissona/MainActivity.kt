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


//        fragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commit()
//
//        bottom_navigation.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.homeFragment -> createFragment(HomeFragment())
//                R.id.dataFragment -> createFragment(DataFragment())
//                else -> return@setOnNavigationItemSelectedListener false
//            }
//        }
//
//        bottom_navigation.setOnNavigationItemReselectedListener {
//            when(it.itemId){
//                R.id.homeFragment -> return@setOnNavigationItemReselectedListener
//                R.id.dataFragment -> return@setOnNavigationItemReselectedListener
//            }
//        }


    }

//    private fun createFragment(fragment: Fragment): Boolean {
//        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
//        return true
//    }

}
