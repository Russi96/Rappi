package com.example.rappitv.seriesmodule.view.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitv.R
import com.example.rappitv.databinding.FragmentSeriesTvBinding
import com.example.rappitv.moviesmodule.adapters.MoviesAdapter
import com.example.rappitv.moviesmodule.viewmodel.MoviesViewModel

import com.example.rappitv.utils.Constants.SERIES_PARAM
import com.example.rappitv.utils.NetworkResult
import com.example.rappitv.utils.showSnackBar
import com.example.rappitv.viewmodel.MainViewModel
import com.example.requestmanager.utils.Constants.DEFAULT_CATEGORIES
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class SeriesTvFragment : Fragment() {

    private lateinit var mBinding: FragmentSeriesTvBinding
    private val mAdapterMovies by lazy { MoviesAdapter() }
    private val moviesViewModel by viewModels<MoviesViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentSeriesTvBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMoviesRecyclerView()
        if (mainViewModel.hasInternetConnection()) {
            getSeriesTv(view)
            mBinding.ltInternetConnection.visibility = View.GONE
            mBinding.rvListMoviesFragmentSeries.visibility = View.VISIBLE
        } else {
            mBinding.ltInternetConnection.visibility = View.VISIBLE
            mBinding.rvListMoviesFragmentSeries.visibility = View.GONE
        }

        mainViewModel.saveWatchType(SERIES_PARAM)

    }

    private fun getSeriesTv(view: View) {
        showShimmerEffect()
        lifecycleScope.launch {
            moviesViewModel.getAllMovies(
                DEFAULT_CATEGORIES,
                SERIES_PARAM,
                mainViewModel.applyQueries()
            )
                .observe(viewLifecycleOwner, { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            hideShimmerEffect()
                            try {
                                val series = response.data
                                if (series != null) {
                                    mAdapterMovies.submitList(series.results)
                                }
                            } catch (e: Exception) {
                                showSnackBar(view, "Recipes not found")
                            }
                        }

                        is NetworkResult.Error -> {
                            hideShimmerEffect()
                            response.message?.let { showSnackBar(view, it) }
                        }

                        is NetworkResult.Loading -> {
                            showShimmerEffect()
                        }
                    }
                })
        }
    }


    private fun setupMoviesRecyclerView() {
        mBinding.rvListMoviesFragmentSeries.adapter = mAdapterMovies
        mBinding.rvListMoviesFragmentSeries.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        val bottomNavigation =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        mBinding.rvListMoviesFragmentSeries.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && bottomNavigation?.isShown == true) {
                    val animationSlideDown =
                        ObjectAnimator.ofFloat(bottomNavigation, "translationY", 200f)
                    animationSlideDown.duration = 1000
                    val slideDown = AnimatorSet()
                    slideDown.play(animationSlideDown)
                    slideDown.start()
                } else if (dy < 0) {
                    val animationSlideUp =
                        ObjectAnimator.ofFloat(bottomNavigation, "translationY", 0f)
                    animationSlideUp.duration = 1000
                    val slideUp = AnimatorSet()
                    slideUp.play(animationSlideUp)
                    slideUp.start()
                }
            }
        })
    }

    private fun showShimmerEffect() {
        mBinding.rvListMoviesFragmentSeries.showShimmer()
    }

    private fun hideShimmerEffect() {
        mBinding.rvListMoviesFragmentSeries.hideShimmer()
    }
}