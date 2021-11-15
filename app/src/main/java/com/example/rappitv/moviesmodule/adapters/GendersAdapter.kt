package com.example.rappitv.moviesmodule.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitv.R
import com.example.rappitv.databinding.ItemGendersBinding
import com.example.rappitv.domain.Genre

class GendersAdapter :
    ListAdapter<Genre, GendersAdapter.MyViewHolder>(DiffUtilCallback) {

    private lateinit var mContext: Context


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = ItemGendersBinding.bind(itemView)
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Genre,
            newItem: Genre
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_genders, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genders = getItem(position)
        holder.mBinding.tvGenderItemGenders.text = genders.name
    }


}