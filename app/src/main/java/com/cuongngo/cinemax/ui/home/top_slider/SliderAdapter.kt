package com.cuongngo.cinemax.ui.home.top_slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemBannerSliderBinding
import com.cuongngo.cinemax.response.Movie

class SliderAdapter internal constructor(
    sliderItems: ArrayList<Movie>,
    viewPager2: ViewPager2
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sliderItems = sliderItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SliderViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_banner_slider,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SliderViewHolder
        val binding = holder.itemBannerSliderBinding
        val movie = sliderItems[position]
        binding.movie = movie
    }

    fun submitList(listMovie: List<Movie>){
        this.sliderItems.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    class SliderViewHolder(
        val itemBannerSliderBinding: ItemBannerSliderBinding
    ): RecyclerView.ViewHolder(itemBannerSliderBinding.root)

}