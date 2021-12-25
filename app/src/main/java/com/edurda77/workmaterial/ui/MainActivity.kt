package com.edurda77.workmaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BasicFragment>(R.id.fragment_container_view)

            }
        }
    }
}