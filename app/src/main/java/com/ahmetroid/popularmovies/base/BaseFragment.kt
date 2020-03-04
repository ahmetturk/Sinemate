package com.ahmetroid.popularmovies.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.ahmetroid.popularmovies.BR
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.db.AppDatabase
import com.ahmetroid.popularmovies.network.Api

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T

    abstract val viewModel: BaseViewModel

    abstract fun getLayoutResId(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<T>(inflater, getLayoutResId(), container, false)
            .apply {
                setVariable(BR.viewModel, viewModel)
                lifecycleOwner = this@BaseFragment
            }
        return binding.root
    }

    protected val repository by lazy {
        Repository(
            Api.apply { language = getString(R.string.language) },
            AppDatabase.getInstance(requireContext())
        )
    }

    protected val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
}