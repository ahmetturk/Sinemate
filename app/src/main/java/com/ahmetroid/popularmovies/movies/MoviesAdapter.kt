package com.ahmetroid.popularmovies.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.databinding.ItemMovieBinding

class MoviesAdapter : PagedListAdapter<Movie, MoviesAdapterViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesAdapterViewHolder(
            ItemMovieBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class MoviesAdapterViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?) {
        movie?.let {
            binding.movie = movie
            binding.movieItemImageView.transitionName = movie.posterPath

            val transitionExtras = FragmentNavigatorExtras(
                binding.movieItemImageView to
                        binding.movieItemImageView.transitionName
            )

            binding.movieItemParent.setOnClickListener {
                it.findNavController().navigate(
                    MoviesFragmentDirections.actionMovieFragmentToDetailFragment(movie),
                    transitionExtras
                )
            }
        }
    }

}