package com.example.rappitv.moviesmodule.view.fragment.web

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.rappitv.R
import com.example.rappitv.databinding.FragmentWebPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebPageFragment : Fragment() {

    private lateinit var mBinding: FragmentWebPageBinding
    private val args: WebPageFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentWebPageBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.web)
        val myWebView: WebView = mBinding.wvFragmentWebPage
        mBinding.clLoadingFragmentDetails.visibility = View.VISIBLE
        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                myWebView.visibility = View.VISIBLE
                mBinding.clLoadingFragmentDetails.visibility = View.GONE
            }
        }
        myWebView.settings.javaScriptEnabled = true
        val websiteUrl: String? = args.webUrl
        if (websiteUrl != null) {
            mBinding.wvFragmentWebPage.loadUrl(websiteUrl)
        }
    }
}