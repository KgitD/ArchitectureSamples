package com.ch999.android.training.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.ch999.android.base.ui.fragment.BaseFragment
import com.ch999.android.training.R
import com.ch999.android.training.databinding.FragmentPagerBinding
import com.ch999.android.training.model.NovelOverviewBean
import com.ch999.android.training.ui.adapter.NovelOverviewAdapter
import com.ch999.android.training.util.TrainingRouterTable
import com.ch999.android.training.vm.NovelViewModel
import com.qmuiteam.qmui.widget.QMUIEmptyView
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration

class PagerFragment(private val pageIndex: Int = 1) : BaseFragment() {
    private var _binding: FragmentPagerBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel by viewModels<NovelViewModel>()

    private lateinit var mData: MutableList<NovelOverviewBean>
    private lateinit var mAdapter: NovelOverviewAdapter

    private lateinit var mEmptyView: QMUIEmptyView
    private lateinit var mItemDecoration: HorizontalDividerItemDecoration

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mEmptyView = QMUIEmptyView(context)
        mEmptyView.setTitleText("暂无数据")

        mData = ArrayList()
        mAdapter = NovelOverviewAdapter(mData)

        mItemDecoration = HorizontalDividerItemDecoration.Builder(context).color(Color.TRANSPARENT).showLastDivider()
            .sizeResId(R.dimen.default_item_decoration_height).build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(mAdapter) {
            setEmptyView(mEmptyView)
            setOnItemClickListener { _, _, position ->
                ARouter.getInstance().build(TrainingRouterTable.NOVEL_DETAILS).withObject("overviews", mData)
                    .withInt("position", position).navigation()

            }
        }

        with(mBinding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(mItemDecoration)
            adapter = mAdapter
        }

        mViewModel.novelOverviews().observe(viewLifecycleOwner, {
            mData.clear()
            mData.addAll(it)
            mAdapter.notifyItemRangeChanged(0, mData.size)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(mBinding.recyclerView) {
            removeItemDecoration(mItemDecoration)
            adapter = null
        }

        _binding?.unbind()
        _binding = null
    }

    override fun tag(): String = "Lifecycle-${javaClass.simpleName}$pageIndex"
}