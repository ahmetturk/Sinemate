package com.ahmetroid.popularmovies.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmetroid.popularmovies.EventObserver
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.base.BaseFragment
import com.ahmetroid.popularmovies.databinding.FragmentMoviesBinding
import com.ahmetroid.popularmovies.util.waitForTransition
import com.google.android.material.snackbar.Snackbar

class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {

    override val viewModel by viewModels<MoviesViewModel> {
        MovieViewModelFactory(repository, sharedPreferences)
    }

    override fun getLayoutResId() = R.layout.fragment_movies

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        // TODO Calculate span count according to screen size
        val gridLayoutManager = GridLayoutManager(context, 3)
        val moviesAdapter = MoviesAdapter()

        binding.moviesRecyclerView.run {
            layoutManager = gridLayoutManager
            adapter = moviesAdapter
        }

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            moviesAdapter.submitList(movies)
        }

        viewModel.showSnackbar.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(
                binding.moviesCoordinatorLayout,
                R.string.connection_error,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.retry) {
                viewModel.retryClicked()
            }.show()
        })

        waitForTransition(binding.moviesRecyclerView)
        return binding.root
    }

}