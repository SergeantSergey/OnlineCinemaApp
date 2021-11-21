package com.example.onlinecinemaapp.feature.moviesList.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.feature.moviesList.domain.module.MovieDomainModel

class MoviesListAdapter(
    private val moviesList: MutableList<MovieDomainModel>
) : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {

    class MoviesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvOverview: TextView = view.findViewById(R.id.tvOverview)
        var ivPreview: ImageView = view.findViewById(R.id.ivPreview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MoviesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        with(holder) {
            tvTitle.text = moviesList[position].title
            tvOverview.text = moviesList[position].overview
            Glide
                .with(holder.itemView)
                .load(moviesList[position].posterPath)
                .centerCrop()
                .into(ivPreview)
        }
    }

    override fun getItemCount(): Int = moviesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieList(list: List<MovieDomainModel>) {
        this.moviesList.addAll(list)
        notifyDataSetChanged()
    }
}