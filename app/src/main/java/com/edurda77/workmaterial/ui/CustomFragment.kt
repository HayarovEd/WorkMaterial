package com.edurda77.workmaterial.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.edurda77.workmaterial.R


class CustomFragment : Fragment() {
   private lateinit var buttonStandart: Button
    private lateinit var buttonSpace: Button
    private lateinit var buttonMoon: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_custom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        buttonStandart = view.findViewById(R.id.standart_theme)
        buttonSpace = view.findViewById(R.id.space_theme)
        buttonMoon = view.findViewById(R.id.moon_theme)
        setTheme()
    }
    private fun setTheme() {
        buttonStandart.setOnClickListener {
            R.style.Theme_WorkMaterial
            requireActivity().recreate()
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(Resources.Theme::class.java.simpleName, R.style.Theme_Space)

            startActivity(intent)
        }
        buttonSpace.setOnClickListener {
            R.style.Theme_Space
            requireActivity().recreate()
           val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(Resources.Theme::class.java.simpleName, R.style.Theme_Space)

            startActivity(intent)

        }
        buttonMoon.setOnClickListener {
            R.style.Theme_Moon
            requireActivity().recreate()
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(Resources.Theme::class.java.simpleName, R.style.Theme_Space)

            startActivity(intent)
        }
    }
}

