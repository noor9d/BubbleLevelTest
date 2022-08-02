package com.example.leveltest

import android.app.Application
import com.example.leveltest.util.PreferenceHelper

class LevelApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.initPrefs(this)
    }
}