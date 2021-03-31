package com.hx.stevenbase.ui.Set.talk

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hx.stevenbase.R

class TalkAdapter(layoutResId: Int, data: MutableList<Data>?) : BaseQuickAdapter<Data, BaseViewHolder>(layoutResId, data) {

    init {
        addChildClickViewIds(R.id.ivCollect, R.id.tvTitle)
    }

    override fun convert(holder: BaseViewHolder, item: Data) {
        holder.setText(R.id.tvAuthor, item.author)
        holder.setText(R.id.tvNiceDate, item.niceDate)
        holder.setText(R.id.tvTitle, item.title)
        holder.setText(R.id.tvChapterName, item.chapterName)
        holder.setImageResource(R.id.ivCollect, if (item.collect) R.drawable.home_recycle_item_icon_like else R.drawable.home_recycle_item_icon_nolike)
    }
}