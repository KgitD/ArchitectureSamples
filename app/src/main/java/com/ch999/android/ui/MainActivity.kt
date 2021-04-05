package com.ch999.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.ch999.android.ui.main.MainContent
import com.ch999.android.ui.theme.ArchitectureSamplesTheme
import com.ch999.android.vm.MainViewModel

class MainActivity : ComponentActivity() {
    private val mViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitectureSamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainContent(vm = mViewModel)
                }
            }
        }
    }
}