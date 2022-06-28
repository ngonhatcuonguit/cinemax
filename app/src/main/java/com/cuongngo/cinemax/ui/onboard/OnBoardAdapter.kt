package com.cuongngo.cinemax.ui.onboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemBoardingSlideBinding

class OnBoardAdapter(
    data: List<OnBoardingModel> = emptyList()
) : RecyclerView.Adapter<OnBoardAdapter.OnBoardViewHolder>() {

    var listImage = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardViewHolder {
        return OnBoardViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_boarding_slide,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardViewHolder, position: Int) {
        val binding = holder.itemBoardingSlideBinding
        binding.ivBoarding.setImageResource(listImage[position].resourceID)
    }

    override fun getItemCount() = listImage.size

    class OnBoardViewHolder(
        val itemBoardingSlideBinding: ItemBoardingSlideBinding
    ) : RecyclerView.ViewHolder(itemBoardingSlideBinding.root)

}