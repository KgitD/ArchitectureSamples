package com.ch999.android.base.util

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

@Suppress("unused")
open class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {
    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var mCurrentState: State = State.IDLE

    open fun onStateChanged(layout: AppBarLayout, state: State, verticalOffset: Int) {

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        when {
            verticalOffset == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    appBarLayout?.let { onStateChanged(appBarLayout, State.EXPANDED, verticalOffset) }
                }
                mCurrentState = State.EXPANDED
            }
            abs(verticalOffset) >= appBarLayout?.totalScrollRange ?: 0 -> {
                if (mCurrentState != State.COLLAPSED) {
                    appBarLayout?.let { onStateChanged(appBarLayout, State.COLLAPSED, verticalOffset) }
                }
                mCurrentState = State.COLLAPSED
            }
            else -> {
                if (mCurrentState != State.IDLE) {
                    appBarLayout?.let { onStateChanged(appBarLayout, State.IDLE, verticalOffset) }
                }
                mCurrentState = State.IDLE
            }
        }
    }
}