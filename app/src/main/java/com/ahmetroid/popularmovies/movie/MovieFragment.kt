package com.ahmetroid.popularmovies.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.base.BaseFragment
import com.ahmetroid.popularmovies.databinding.FragmentMovieBinding

class MovieFragment : BaseFragment<FragmentMovieBinding>() {

    override val viewModel by viewModels<MovieViewModel>()

    override fun getLayoutResId() = R.layout.fragment_movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.getMovie()

        return binding.root
    }

}