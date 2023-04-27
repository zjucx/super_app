package com.cx.profile

import android.app.Application
import android.content.Context
import android.util.Log
import com.cx.profile.inject.ApplicationModule

public class Profile : Application() {
    private var Tag : String? = "Profile";

    override fun onCreate() {
        super.onCreate()
        Log.i(Tag, "Profile init")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ApplicationModule.init()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}