package com.ch999.android.base.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.ch999.android.base.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

open class BaseApplication : AutoSizeApplication(){
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initARouter(this)
    }

    private fun initLogger() {
        val strategy = PrettyFormatStrategy.newBuilder().showThreadInfo(false).methodCount(0)
            .methodOffset(0).logStrategy(LogcatLogStrategy()).build()
        Logger.addLogAdapter(AndroidLogAdapter(strategy))
    }

    private fun initARouter(application: Application) {
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations
            // will be
            // invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(application) // As early as possible, it is recommended to initialize in the Application
    }
}