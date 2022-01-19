package com.edurda77.workmaterial.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.databinding.ActivityCustomBinding


class CustomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCustomBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CustomFragment>(R.id.fragment_container_custom_view)

            }
        }

    }


}