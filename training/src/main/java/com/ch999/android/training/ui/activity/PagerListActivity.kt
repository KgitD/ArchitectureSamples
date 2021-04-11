package com.ch999.android.training.ui.activity

import android.os.Bundle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ch999.android.base.ui.activity.BaseActivity
import com.ch999.android.training.R
import com.ch999.android.training.databinding.ActivityPagerListBinding
import com.ch999.android.training.ui.adapter.PagerListPagerAdapter
import com.ch999.android.training.util.TrainingRouterTable
import com.google.android.material.tabs.TabLayout
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout.OnOffsetUpdateListener

@Route(path = TrainingRouterTable.PAGER_LIST, name = "ViewPager + FragmentPagerAdapter")
class PagerListActivity : BaseActivity() {
    private var _binding: ActivityPagerListBinding? = null
    private val mBinding get() = _binding!!

    /**
     * 添加并实现页面切换观察者，保证页面切换时缓存已加载的页面
     */
    private val mOnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            if (position > mBinding.viewPager.offscreenPageLimit) {
                mBinding.viewPager.offscreenPageLimit = position
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    /**
     * 监听头部可折叠导航栏的状态，当折叠/展开时改变标签布局样式。
     */
    private val mOnOffsetUpdateListener = object : OnOffsetUpdateListener {
        private var mExpanded: Boolean? = null
        override fun onOffsetChanged(layout: QMUICollapsingTopBarLayout?, offset: Int, expandFraction: Float) {
            val expanded = expandFraction != 1.0f
            if (mExpanded != expanded) {
                // FIXED:requestLayout() improperly called by androidx.appcompat.widget.AppCompatTextView
                mExpanded = expanded
                mBinding.tabLayout.toggleState(isAppBarExpanded = mExpanded!!)
            }
            mBinding.toolbar.alpha = 1.0f - expandFraction
        }
    }

    /**
     * 当头部布局折叠/展开时改变标签布局样式。
     */
    private fun TabLayout?.toggleState(isAppBarExpanded: Boolean) = with(this) {
        val normalColor = Color(if (isAppBarExpanded) 0xFFF5F5F5 else 0xFF666666).toArgb()
        val selectedColor = Color(if (isAppBarExpanded) 0xFFFFFFFF else 0xFF333333).toArgb()
        this?.setTabTextColors(normalColor, selectedColor)
        this?.setSelectedTabIndicatorColor(selectedColor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_pager_list)

        with(mBinding.toolbar) {
            setBottomDividerAlpha(0)
            addLeftBackImageButton().setOnClickListener { supportFinishAfterTransition() }
        }

        Glide.with(this).load(R.drawable.ic_pager_list_top_bar_bg).diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true).into(mBinding.image)

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager, true)
        mBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

        mBinding.tabLayout.toggleState(true)
        mBinding.collapsingTopBar.addOnOffsetUpdateListener(mOnOffsetUpdateListener)

        with(mBinding.viewPager) {
            offscreenPageLimit = 1 // 页面初始化时设置预加载页面数量为1，加快加载速度
            currentItem = 0 // 配合offscreenPageLimit保证页面初始化时只加载最多两页
            addOnPageChangeListener(mOnPageChangeListener) // 添加并实现页面切换观察者，保证页面切换时缓存已加载的页面
            adapter = PagerListPagerAdapter(supportFragmentManager)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mBinding.collapsingTopBar.removeOnOffsetUpdateListener(mOnOffsetUpdateListener)

        mBinding.viewPager.removeOnPageChangeListener(mOnPageChangeListener)
        mBinding.viewPager.adapter = null

        _binding?.unbind()
        _binding = null
    }
}