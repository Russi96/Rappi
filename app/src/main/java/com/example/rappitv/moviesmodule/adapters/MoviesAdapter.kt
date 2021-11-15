package com.example.rappitv.moviesmodule.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagemanager.bindImageUrl
import com.example.rappitv.R
import com.example.rappitv.databinding.ItemMoviesBinding
import com.example.rappitv.domain.ResultX
import com.example.rappitv.moviesmodule.view.activities.DetailsActivity
import com.example.rappitv.utils.Constants.MOVIE_ACTIVITY_ID
import com.example.rappitv.utils.Constants.POSTER_URL

class MoviesAdapter : ListAdapter<ResultX, MoviesAdapter.MyViewHolder>(DiffUtilCallback) {

    private lateinit var mContext: Context


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = ItemMoviesBinding.bind(itemView)
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<ResultX>() {
        override fun areItemsTheSame(
            oldItem: ResultX,
            newItem: ResultX
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResultX,
            newItem: ResultX
        ): Boolean = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = getItem(position)
        holder.mBinding.apply {
            if (movies.title.isNullOrEmpty()) {
                tvMoveName.text = movies.name
            } else {
                tvMoveName.text = movies.title
            }

            if (movies.releaseDate.isNullOrEmpty()){
                tvReleaseDate.text = movies.first_air_date
            }else {
                tvReleaseDate.text = movies.releaseDate
            }

            ratingProgressbarItemMovies.progress = movies.voteAverage.toInt()
            tvRatingItemMovies.text = movies.voteAverage.toString()
            val uriMovies = POSTER_URL + movies.posterPath
            ivMoveImage.bindImageUrl(
                uriMovies,
                R.drawable.rappi_background,
                R.drawable.rappi_background
            )
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra(MOVIE_ACTIVITY_ID, movies.id)
            mContext.startActivity(intent)
        }
    }


}