package com.ahmetroid.popularmovies.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.databinding.ItemMovieBinding

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapterViewHolder>() {

    private var list = emptyList<Movie>()
    private var previousSize = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesAdapterViewHolder(
            ItemMovieBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MoviesAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setList(movies: List<Movie>) {
        list = movies
        notifyItemRangeInserted(previousSize, movies.size - previousSize)
        previousSize = movies.size
    }
}

class MoviesAdapterViewHolder(val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movie = movie
    }
}