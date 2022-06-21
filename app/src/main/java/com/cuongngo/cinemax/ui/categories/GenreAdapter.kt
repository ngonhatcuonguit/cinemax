package com.cuongngo.cinemax.ui.categories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ViewBindingAdapter.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemCategoriesBinding
import com.cuongngo.cinemax.response.GenresMovie

class GenreAdapter(
    listGenres: ArrayList<GenresMovie>,
    private val selectedListener: SelectedListener
): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val listGenres = listGenres

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_categories,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val binding = holder.itemCategoriesBinding
        val item = listGenres[position]
        val paddingStart = App.getResources().getDimensionPixelOffset(R.dimen._24dp)

        binding.genre = item

        if (item == listGenres.firstOrNull()){
            binding.clContainer.setPadding(paddingStart,0,0,0)
        }else binding.clContainer.setPadding(0,0,0,0)

        binding.root.setOnClickListener {
            selectedListener.onSelectedListener(item)
        }
        if (item.is_selected){
            binding.tvGenre.setBackgroundColor(Color.parseColor("#252836"))
            binding.tvGenre.setTextColor(Color.parseColor("#12CDD9"))
        }else {
            binding.tvGenre.setBackgroundColor(Color.parseColor("#00000000"))
            binding.tvGenre.setTextColor(Color.parseColor("#EBEBEF"))
        }
    }

    override fun getItemCount(): Int {
        return listGenres.size
    }

    fun submitListGenres(listGenres: List<GenresMovie>){
        this.listGenres.addAll(listGenres)
        notifyDataSetChanged()
    }

    interface SelectedListener {
        fun onSelectedListener(genre: GenresMovie)
    }

    class GenreViewHolder(
        val itemCategoriesBinding: ItemCategoriesBinding
    ): RecyclerView.ViewHolder(itemCategoriesBinding.root)
}