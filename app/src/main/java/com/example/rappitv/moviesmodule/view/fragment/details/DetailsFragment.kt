package com.example.rappitv.moviesmodule.view.fragment.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rappitv.R
import com.example.rappitv.databinding.FragmentDetailsBinding
import com.example.rappitv.domain.Video
import com.example.rappitv.moviesmodule.adapters.GendersAdapter
import com.example.rappitv.moviesmodule.viewmodel.MoviesViewModel
import com.example.rappitv.utils.Constants.MOVIE_PARAM
import com.example.rappitv.utils.NetworkResult
import com.example.rappitv.utils.observeOnce
import com.example.rappitv.utils.showSnackBar
import com.example.rappitv.viewmodel.MainViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val moviesViewModel by viewModels<MoviesViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var mBinding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val mAdapterGender by lazy { GendersAdapter() }
    private lateinit var video: Video
    private var backgroundImage: String = ""
    private lateinit var webUrl: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGendersRecyclerView()
        val videoMovie: YouTubePlayerView = mBinding.youtubePlayerView
        lifecycle.addObserver(videoMovie)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.details)
        mainViewModel.readWatch.observeOnce(viewLifecycleOwner, { typeWatch ->
            if (mainViewModel.hasInternetConnection()) {
                getDetailMovies(view, typeWatch)
                getMoviesVideo(videoMovie, view, typeWatch)
            }
        })


        mBinding.btVideoFragmentDetails.setOnClickListener {
            val action =
                DetailsFragmentDirections.actionDetailsFragmentToVideosMovieFragment(video)
            findNavController().navigate(action)
        }

        mBinding.btPageFragmentDetails.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToWebPageFragment(webUrl)
            findNavController().navigate(action)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun getDetailMovies(view: View, watch: String) {
        mBinding.clLoadingFragmentDetails.visibility = View.VISIBLE
        lifecycleScope.launch {
            moviesViewModel.getDetailsMovies(
                args.movieFragmentId,
                watch,
                mainViewModel.applyQueries()
            )
                .observe(viewLifecycleOwner, { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            val movieDetails = response.data
                            if (movieDetails != null) {
                                //toMutableList help to list update
                                with(mBinding) {
                                    if (watch == MOVIE_PARAM) {
                                        tvNameMoveActivityDetails.text = movieDetails.title
                                        tvReleaseDateMovieActivityDetails.text =
                                            movieDetails.releaseDate
                                    } else {
                                        tvNameMoveActivityDetails.text = movieDetails.name
                                        tvReleaseDateMovieActivityDetails.text =
                                            movieDetails.first_air_date
                                    }

                                    tvDescriptionMovieFragmentDetails.text = movieDetails.overview

                                    tvOriginalLanguageMovieActivityDetails.text =
                                        movieDetails.originalLanguage
                                    tvRunTimeMovieActivityDetails.text =
                                        "${movieDetails.runtime} min"
                                    tvStatusMovieActivityDetails.text = movieDetails.status
                                    tvRatingMovieActivityDetails.text =
                                        movieDetails.voteAverage.toString()
                                }
                                backgroundImage = movieDetails.backdropPath
                                webUrl = movieDetails.homepage
                                mAdapterGender.submitList(movieDetails.genres)
                                mBinding.clLoadingFragmentDetails.visibility = View.GONE
                            }
                        }

                        is NetworkResult.Error -> {
                            response.message?.let { showSnackBar(view, it) }
                            mBinding.clLoadingFragmentDetails.visibility = View.GONE
                        }

                        is NetworkResult.Loading -> {
                            mBinding.clLoadingFragmentDetails.visibility = View.VISIBLE
                        }
                    }
                })
        }
    }

    private fun getMoviesVideo(videoMovie: YouTubePlayerView, view: View, watch: String) {
        lifecycleScope.launch {
            moviesViewModel.getMovieVideo(args.movieFragmentId, watch, mainViewModel.applyQueries())
                .observe(viewLifecycleOwner, { response ->
                    when (response) {
                        is NetworkResult.Success -> {
                            mBinding.clLoadingFragmentDetails.visibility = View.GONE
                            val movieVideo = response.data
                            if (movieVideo != null) {
                                video = Video(
                                    moviesVIdeo = movieVideo,
                                    imageBackground = backgroundImage
                                )
                                videoMovie.addYouTubePlayerListener(object :
                                    AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        val videoId = movieVideo.results[0].key
                                        youTubePlayer.loadVideo(videoId, 0F)
                                    }

                                })
                            }
                        }

                        is NetworkResult.Error -> {
                            response.message?.let { showSnackBar(view, it) }
                            mBinding.clLoadingFragmentDetails.visibility = View.GONE
                        }

                        is NetworkResult.Loading -> {
                            mBinding.clLoadingFragmentDetails.visibility = View.VISIBLE
                        }
                    }
                })
        }
    }

    private fun setupGendersRecyclerView() {
        mBinding.rvListItemsGendersFragmentDetails.adapter = mAdapterGender
        mBinding.rvListItemsGendersFragmentDetails.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }
}