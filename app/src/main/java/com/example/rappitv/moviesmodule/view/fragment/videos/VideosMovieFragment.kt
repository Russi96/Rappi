package com.example.rappitv.moviesmodule.view.fragment.videos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagemanager.bindImageUrl
import com.example.rappitv.R
import com.example.rappitv.databinding.FragmentVideosMovieBinding
import com.example.rappitv.moviesmodule.adapters.VideosAdapter
import com.example.rappitv.utils.Constants.BACKDROP_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideosMovieFragment : Fragment() {

    private lateinit var mBinding: FragmentVideosMovieBinding
    private val args by navArgs<VideosMovieFragmentArgs>()
    private val mAdapterVideos by lazy { VideosAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentVideosMovieBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVideoRecyclerView()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.videos)
        val backgroundImage = BACKDROP_URL + args.video?.imageBackground
        mBinding.ivBackgroundImageItemVideo.bindImageUrl(
            url = backgroundImage,
            placeholder = R.drawable.rappi_background,
            errorPlaceholder = R.drawable.rappi_background
        )
        mAdapterVideos.submitList(args.video?.moviesVIdeo?.results)
    }

    private fun setupVideoRecyclerView() {
        mBinding.rvVideoFragmentVideos.adapter = mAdapterVideos
        mBinding.rvVideoFragmentVideos.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }
}