package com.edurda77.workmaterial.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.edurda77.workmaterial.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigaion: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_Moon)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigaion = findViewById(R.id.bottom_navigation_view)
        bottomNavigaion.itemIconTintList = null
        setMenu()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BasicFragment>(R.id.fragment_container_view)

            }
        }

    }
    private fun setMenu() {
        bottomNavigaion.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.image_earth -> {
                    item.isChecked = true
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view,EarthFragment())

                    }

                }

                R.id.image_mars -> {
                    item.isChecked = true
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view,MarsFragment())


                    }
                }

                R.id.image_search -> {
                    item.isChecked = true
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view,PhotoFragment())
                    }


                }

                R.id.home_start -> {
                    item.isChecked = true
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view,BasicFragment())

                    }


                }
                R.id.image_last_day -> {
                    item.isChecked = true
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container_view,LastFragment())

                    }


                }
            }
            true
        }
    }

}