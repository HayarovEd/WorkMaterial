package com.edurda77.workmaterial.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}