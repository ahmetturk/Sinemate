package com.ahmetroid.popularmovies.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.base.BaseFragment
import com.ahmetroid.popularmovies.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override val viewModel by viewModels<DetailViewModel> { DetailViewModelFactory(args.movie) }

    override fun getLayoutResId() = R.layout.fragment_detail

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.detailCollapsingToolbarLayout.setupWithNavController(
            binding.detailToolbar,
            findNavController(),
            AppBarConfiguration(findNavController().graph)
        )

        return binding.root
    }
}