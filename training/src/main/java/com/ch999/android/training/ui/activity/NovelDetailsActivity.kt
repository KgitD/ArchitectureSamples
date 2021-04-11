package com.ch999.android.training.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ch999.android.base.recycler.GalleryLayoutManager
import com.ch999.android.base.ui.activity.BaseActivity
import com.ch999.android.training.R
import com.ch999.android.training.databinding.ActivityNovelDetailsBinding
import com.ch999.android.training.model.NovelDetailsBean
import com.ch999.android.training.model.NovelOverviewBean
import com.ch999.android.training.ui.adapter.NovelDetailsAdapter
import com.ch999.android.training.util.TikTokSnapHelper
import com.ch999.android.training.util.TrainingRouterTable
import com.ch999.android.training.vm.NovelDetailsViewModel


@Route(path = TrainingRouterTable.NOVEL_DETAILS, name = "ViewPager + FragmentPagerAdapter")
class NovelDetailsActivity : BaseActivity() {

    @Autowired(name = "position")
    @JvmField
    var position: Int? = 0

    @Autowired(name = "overviews")
    @JvmField
    var mOverviews: MutableList<NovelOverviewBean>? = null

    private var _binding: ActivityNovelDetailsBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel by viewModels<NovelDetailsViewModel>()

    private lateinit var mData: MutableList<NovelDetailsBean>
    private lateinit var mAdapter: NovelDetailsAdapter

    private lateinit var mItemDecoration: RecyclerView.ItemDecoration
    private val mSnapHelper: SnapHelper = TikTokSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        if (mOverviews.isNullOrEmpty()) {
            supportFinishAfterTransition()
            return
        }

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_novel_details)

        mData = ArrayList()
        mAdapter = NovelDetailsAdapter(mData)

        mItemDecoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        (mItemDecoration as DividerItemDecoration).setDrawable(
            ResourcesCompat.getDrawable(
                resources, R.drawable
                    .divider_item_decoration_default, null
            )!!
        )

        with(mBinding.recyclerView) {
            layoutManager = with(GalleryLayoutManager()) {
                widthPercent = 0.90f
                heightPercent = 1.0f
                rotationDegree = 0.0f
                scaleYFactor = 0.0f
                alphaFactor = 0.2f
                this
            }
            addItemDecoration(mItemDecoration)
            adapter = mAdapter
            mSnapHelper.attachToRecyclerView(this)
            scrollToPosition(position ?: 0)
        }

        mViewModel.novelDetailsList().observe(this, {
            mData.clear()
            mData.addAll(it)
            mAdapter.notifyItemRangeChanged(0, mData.size)
            mBinding.recyclerView.scrollToPosition(position ?: 0)
        })

        mOverviews?.let { mViewModel.refreshNovelDetailsList(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        with(mBinding.recyclerView) {
            removeItemDecoration(mItemDecoration)
            adapter = null
            mSnapHelper.attachToRecyclerView(null)
        }

        _binding?.unbind()
        _binding = null
    }

}