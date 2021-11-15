package com.example.rappitv.moviesmodule.view.fragment.movies

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitv.R
import com.example.rappitv.data.database.entities.MoviesEntity
import com.example.rappitv.databinding.FragmentMoviesBinding
import com.example.rappitv.domain.Movies
import com.example.rappitv.domain.ResultX
import com.example.rappitv.moviesmodule.adapters.MoviesAdapter

import com.example.rappitv.moviesmodule.viewmodel.MoviesViewModel
import com.example.rappitv.utils.Constants.MOVIE_PARAM

import com.example.rappitv.utils.NetworkListener
import com.example.rappitv.utils.NetworkResult
import com.example.rappitv.utils.observeOnce
import com.example.rappitv.utils.showSnackBar
import com.example.rappitv.viewmodel.MainViewModel
import com.example.requestmanager.utils.Constants.DEFAULT_CATEGORIES
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var mBinding: FragmentMoviesBinding
    private val moviesViewModel by viewModels<MoviesViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private val itemList: MutableList<ResultX> = mutableListOf()
    private val args: MoviesFragmentArgs by navArgs()
    private var moviesResultX: MutableList<ResultX> = mutableListOf()
    var categoriesType = DEFAULT_CATEGORIES
    private lateinit var networkListener: NetworkListener
    private val mAdapterMovies by lazy { MoviesAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMoviesRecyclerView()
        mainViewModel.readBackOnline.observe(viewLifecycleOwner, {
            mainViewModel.backOnline = it
        })

        mainViewModel.saveWatchType(MOVIE_PARAM)

        lifecycleScope.launchWhenCreated {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext()).collect { status ->
                mainViewModel.networkStatus = status
                mainViewModel.showNetworkStatus()
                readMoviesDatabase(view)
            }
        }
        mBinding.moviesFab.setOnClickListener {
            if (mainViewModel.networkStatus) {
                findNavController().navigate(R.id.action_moviesFragment_to_moviesBottomSheet)
            } else {
                mainViewModel.showNetworkStatus()
            }
        }

        mainViewModel.readCategoriesType.asLiveData().observe(viewLifecycleOwner, { response ->
            categoriesType = response.selectedCategory
        })

        searchMovies()

    }

    private fun searchMovies() {
        mBinding.svSearchMoviesFragmentMovie.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchThroughDatabase(newText)
                }
                return true
            }

        })
    }

    private fun searchThroughDatabase(query: String) {
        moviesResultX.clear()
        var searchQuery = query
        searchQuery = "$searchQuery%"
        moviesViewModel.searchMovies(searchQuery)
            .observe(viewLifecycleOwner, { result ->
                if (!result.isNullOrEmpty()) {
                    result.forEach {
                        moviesResultX.add(it.movies)
                    }
                    //Update List to adapter
                    mAdapterMovies.submitList(moviesResultX.toMutableList())
                    mBinding.rvListMoviesFragmentMovies.visibility = View.VISIBLE
                    mBinding.ltEmptyList.visibility = View.GONE
                } else {
                    mBinding.rvListMoviesFragmentMovies.visibility = View.GONE
                    mBinding.ltEmptyList.visibility = View.VISIBLE
                }
            })
    }

    private fun readMoviesDatabase(view: View) {
        lifecycleScope.launch {
            moviesViewModel.readMovies.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    database.forEach { items ->
                        itemList.add(items.movies)
                    }
                    mAdapterMovies.submitList(itemList)
                } else {
                    if (mainViewModel.hasInternetConnection()) {
                        requestApiMovies(view)
                    } else {
                        showSnackBar(view, "Not Internet Connection")
                    }
                }
            })
        }

    }

    private fun requestApiMovies(view: View) {
        lifecycleScope.launch {
            moviesViewModel.getAllMovies(categoriesType, MOVIE_PARAM, mainViewModel.applyQueries())
                .observe(viewLifecycleOwner, { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            try {
                                val movies = response.data
                                if (movies != null) {
                                    offlineCacheMovies(movies)
                                    mAdapterMovies.submitList(movies.results)
                                }
                            } catch (e: Exception) {
                                showSnackBar(view, "Recipes not found")
                            }
                        }

                        is NetworkResult.Error -> {
                            response.message?.let { showSnackBar(view, it) }
                        }

                        is NetworkResult.Loading -> {

                        }
                    }
                })
        }
    }

    private fun offlineCacheMovies(movies: Movies) {
        movies.results.forEach { item ->
            val moviesEntity = MoviesEntity(item)
            moviesViewModel.insertMovies(moviesEntity)
        }
    }

    private fun setupMoviesRecyclerView() {
        mBinding.rvListMoviesFragmentMovies.adapter = mAdapterMovies
        mBinding.rvListMoviesFragmentMovies.layoutManager =
            GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        mBinding.rvListMoviesFragmentMovies.addOnScrollListener(object :
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


}