package com.ch999.android.base.ui.activity

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

open class BaseActivity : AutoSizeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        QMUIStatusBarHelper.translucent(this)
    }
}