package com.ch999.android.training.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ch999.android.training.R
import com.ch999.android.training.databinding.ItemNovelDetailsBinding
import com.ch999.android.training.model.NovelDetailsBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class NovelDetailsAdapter(data: MutableList<NovelDetailsBean>) :
    BaseQuickAdapter<NovelDetailsBean, BaseDataBindingHolder<ItemNovelDetailsBinding>>(
        R.layout.item_novel_details,
        data
    ) {
    private var mOutlineInset: Int = 0
    private var mCornerRadius: Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        with(context.resources) {
            mOutlineInset = getDimensionPixelSize(R.dimen.default_card_view_elevation)
            mCornerRadius = getDimensionPixelSize(R.dimen.default_card_view_corner_radius)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mOutlineInset = 0
        mCornerRadius = 0
    }

    override fun convert(holder: BaseDataBindingHolder<ItemNovelDetailsBinding>, item: NovelDetailsBean) =
        with(holder.dataBinding) {
            this?.container?.setRadiusAndShadow(mCornerRadius, mOutlineInset, 1.0f)
            this?.container?.setOutlineInset(mOutlineInset, mOutlineInset, mOutlineInset, mOutlineInset)
            this?.title?.text = item.title; this?.subtitle?.text = item.subtitle
            this?.content?.text = item.content
            this?.summary?.text = item.summary
        }
}