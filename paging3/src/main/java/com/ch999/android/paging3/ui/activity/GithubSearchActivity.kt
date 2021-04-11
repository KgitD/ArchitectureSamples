package com.ch999.android.paging3.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.ch999.android.base.ui.activity.BaseActivity
import com.ch999.android.paging3.ui.github.GitHubSearchScreen
import com.ch999.android.paging3.ui.theme.ArchitectureSamplesTheme
import com.ch999.android.paging3.ui.theme.GitHubStatusBarColor
import com.ch999.android.paging3.vm.GithubSearchViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Route(path = "/paging3/github_search", name = "Github搜索页面")
class GithubSearchActivity : BaseActivity() {
    private val mViewModel by viewModels<GithubSearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color(0xFFADD9C9).toArgb()
        setContent {
            ArchitectureSamplesTheme {
                ProvideWindowInsets(consumeWindowInsets = true) {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        GitHubSearchScreen(mViewModel, onBack = { finishAfterTransition() })
                    }
                }
            }
        }
    }
}