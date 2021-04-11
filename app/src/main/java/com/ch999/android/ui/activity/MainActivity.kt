package com.ch999.android.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.ch999.android.base.ui.activity.BaseActivity
import com.ch999.android.ui.main.MainContent
import com.ch999.android.ui.theme.ArchitectureSamplesTheme
import com.ch999.android.ui.theme.TealGreen
import com.ch999.android.vm.MainViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@ExperimentalFoundationApi
class MainActivity : BaseActivity() {
    private val mViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = TealGreen.toArgb()
        setContent {
            ArchitectureSamplesTheme {
                // https://google.github.io/accompanist/insets/
                ProvideWindowInsets(consumeWindowInsets = true) {
                    // A surface container using the 'background' color from the theme
                    Surface(color = TealGreen) {
                        MainContent(vm = mViewModel)
                    }
                }
            }
        }
    }
}