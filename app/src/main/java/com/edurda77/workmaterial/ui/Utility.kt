package com.edurda77.workmaterial.ui

import android.app.Activity
import android.content.Intent
const val THEME_STANDART = 1
const val THEME_SPACE = 2
const val THEME_MOON = 3

object  Utility {
    private var sTheme = 0


    fun changeToTheme(activity: Activity, theme: Int) {
        sTheme = theme
        activity.finish()
        activity.startActivity(Intent(activity, activity.javaClass))
    }


    fun onActivityCreateSetTheme(activity: Activity) {
        when (sTheme) {
            THEME_STANDART -> activity.setTheme(com.edurda77.workmaterial.R.style.Theme_WorkMaterial)
            THEME_SPACE -> activity.setTheme(com.edurda77.workmaterial.R.style.Theme_Space)
            THEME_MOON -> activity.setTheme(com.edurda77.workmaterial.R.style.Theme_Moon)
            else -> activity.setTheme(com.edurda77.workmaterial.R.style.Theme_WorkMaterial)
        }
    }
}