package com.ahmetroid.popularmovies.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionInflater
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.base.BaseFragment
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.databinding.FragmentDetailBinding
import com.ahmetroid.popularmovies.db.AppDatabase
import com.ahmetroid.popularmovies.network.Api
import com.ahmetroid.popularmovies.recyclerview.HorizontalItemDecoration

class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    override val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            resources, args.movie,
            Repository(
                Api.apply { language = getString(R.string.language) },
                AppDatabase.getInstance(requireContext())
            )
        )
    }

    override fun getLayoutResId() = R.layout.fragment_detail

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

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

        val videosAdapter = VideosAdapter(videosAdapterListener)
        binding.videosRecyclerView.apply {
            adapter = videosAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            addItemDecoration(HorizontalItemDecoration(resources))
        }
        viewModel.videos.observe(viewLifecycleOwner) { videos ->
            videosAdapter.setList(videos)
        }

        return binding.root
    }

    private val videosAdapterListener = { videoUrl: String ->
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:$videoUrl")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=$videoUrl")
                )
            )
        }
    }
}