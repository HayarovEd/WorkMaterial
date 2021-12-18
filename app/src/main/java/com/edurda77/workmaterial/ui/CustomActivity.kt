package com.edurda77.workmaterial.ui

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.databinding.ActivityCustomBinding
import com.edurda77.workmaterial.databinding.ActivityMainBinding

class CustomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCustomBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.standartTheme.setOnClickListener {
            setTheme(R.style.Theme_WorkMaterial)
            startActivity ()
        }
        binding.spaceTheme.setOnClickListener {
            setTheme(R.style.Theme_Space)
            startActivity ()
        }
        binding.moonTheme.setOnClickListener {
            setTheme(R.style.Theme_Moon)
            startActivity ()
        }
    }
    private fun startActivity () {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}