package com.ch999.android.paging3.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.toArgb
import com.alibaba.android.arouter.facade.annotation.Route
import com.ch999.android.paging3.ui.github.GitHubSearchScreen
import com.ch999.android.paging3.ui.theme.ArchitectureSamplesTheme
import com.ch999.android.paging3.ui.theme.GitHubStatusBarColor
import com.ch999.android.paging3.vm.GithubSearchViewModel

@Route(path = "/paging3/github_search", name = "Github搜索页面")
class GithubSearchActivity : ComponentActivity() {
    private val mViewModel by viewModels<GithubSearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = GitHubStatusBarColor.toArgb()
        setContent {
            ArchitectureSamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    GitHubSearchScreen(mViewModel, onBack = { finishAfterTransition() })
                }
            }
        }
    }
}