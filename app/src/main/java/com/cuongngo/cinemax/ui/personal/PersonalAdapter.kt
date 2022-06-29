package com.cuongngo.cinemax.ui.personal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ViewBindingAdapter.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.cuongngo.cinemax.App
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.databinding.ItemPersonalBinding
import com.cuongngo.cinemax.response.Personal

class PersonalAdapter(
    listPersonal: List<Personal>
): RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>() {

    private val listPersonal = listPersonal as ArrayList<Personal>

    class PersonalViewHolder(
        val itemPersonal: ItemPersonalBinding
    ) : RecyclerView.ViewHolder(itemPersonal.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalViewHolder {
        return PersonalViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_personal,
                parent,
                false
                )
        )
    }

    override fun onBindViewHolder(holder: PersonalViewHolder, position: Int) {
        val personal = listPersonal[position]
        holder.itemPersonal.personal = personal
        if (position == 0){
            val paddingStart = App.getResources().getDimensionPixelOffset(R.dimen._24dp)
            val paddingEnd = App.getResources().getDimensionPixelOffset(R.dimen._12dp)
            holder.itemPersonal.clContainer.setPadding(paddingStart,0,paddingEnd,0)
        }
    }

    override fun getItemCount() = listPersonal.size

    fun submitList(listPersonal: List<Personal>){
        this.listPersonal.addAll(listPersonal)
        notifyDataSetChanged()
    }
}