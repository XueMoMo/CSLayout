package com.eericxu.cslayout

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DisplayUtils.init(this)
    }
}