package com.kldaji.android_medium

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kldaji.android_medium.databinding.ItemPageBinding

class MainViewPager2Adapter : RecyclerView.Adapter<MainViewPager2Adapter.PageViewHolder>() {
    private val dummyDataList = listOf(Unit, Unit, Unit, Unit)

    override fun getItemCount() = dummyDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PageViewHolder(
        ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) = Unit

    class PageViewHolder(binding: ItemPageBinding) :
        RecyclerView.ViewHolder(binding.root)
}
