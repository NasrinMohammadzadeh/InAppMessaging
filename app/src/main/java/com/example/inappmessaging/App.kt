package com.example.inappmessaging

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.example.inappmessaging.App

@HiltAndroidApp
class App : Application() {
    companion object {
        private var instance: App? = null
        fun getInstance(): App? {
            checkNotNull(instance)
            return instance
        }
    }

    init {
        instance = this
    }
}