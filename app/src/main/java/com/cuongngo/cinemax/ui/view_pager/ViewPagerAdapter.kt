package com.cuongngo.cinemax.ui.view_pager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemBannerSliderBinding
import com.cuongngo.cinemax.response.Movie

class ViewPagerAdapter (
    private val data: List<Movie> = emptyList(),
    private val viewPager2: ViewPager2,
    private val onDetachFromWindow: (() -> Unit)? = null,
    private val onAttachToWindow: (() -> Unit)? = null,
    private val onItemClick: ((Movie) -> Unit)? = null
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private var infiniteData: MutableList<Movie> = ArrayList()

    init {
        infiniteData.apply {
            infiniteData.addAll(data)
            infiniteData.addAll(data)
            infiniteData.addAll(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_banner_slider,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val binding = holder.itemBannerSliderBinding
        val movie = infiniteData[position]
        binding.movie = movie
        binding.root.setOnClickListener {
            onItemClick?.invoke(movie)
        }

    }

    fun submitList(listMovie: ArrayList<Movie>){
        this.infiniteData.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return infiniteData.size
    }

    fun getRealItemCount() = itemCount/3

    fun getRealPosition(position: Int)  = position % data.size

    override fun onViewAttachedToWindow(holder: ViewPagerViewHolder) {
        super.onViewAttachedToWindow(holder)
        onDetachFromWindow?.invoke()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        onAttachToWindow?.invoke()
    }

    class ViewPagerViewHolder(
        val itemBannerSliderBinding: ItemBannerSliderBinding
    ): RecyclerView.ViewHolder(itemBannerSliderBinding.root)

}