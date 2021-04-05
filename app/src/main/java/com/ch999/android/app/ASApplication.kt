package com.ch999.android.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.ch999.android.BuildConfig

class ASApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initArouter(this)
    }

    private fun initArouter(application: Application) {
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations
            // will be
            // invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(application) // As early as possible, it is recommended to initialize in the Application
    }
}