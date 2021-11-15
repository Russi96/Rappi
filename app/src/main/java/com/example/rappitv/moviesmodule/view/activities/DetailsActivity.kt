package com.example.rappitv.moviesmodule.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.rappitv.R
import com.example.rappitv.databinding.ActivityDetailsBinding
import com.example.rappitv.utils.Constants.MOVIE_ACTIVITY_ID
import com.example.rappitv.utils.Constants.MOVIE_FRAGMENT_ID
import com.example.rappitv.viewmodel.MainViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDetailsBinding
    private lateinit var navController: NavController
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val movieId = intent.getIntExtra(MOVIE_ACTIVITY_ID, 0)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostDetailsFragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_host_details)

        navController = navHostFragment.navController
        val bundle = Bundle()
        bundle.putInt(MOVIE_FRAGMENT_ID, movieId)
        navController.setGraph(graph, bundle)

        if (mainViewModel.hasInternetConnection()) {
            mBinding.navHostDetailsFragment.visibility = View.VISIBLE
            mBinding.ltInternetConnection.visibility = View.GONE
        } else {
            mBinding.navHostDetailsFragment.visibility = View.GONE
            mBinding.ltInternetConnection.visibility = View.VISIBLE
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}