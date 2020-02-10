package com.ahmetroid.popularmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmetroid.popularmovies.databinding.FragmentBaseBinding

class BaseFragment : Fragment() {

    private lateinit var binding: FragmentBaseBinding

    private val viewModel by viewModels<BaseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getMovie()

        return binding.root
    }

}