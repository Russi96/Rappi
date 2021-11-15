package com.example.rappitv.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rappitv.BuildConfig.API_KEY
import com.example.rappitv.data.DataStoreRepository
import com.example.requestmanager.utils.Constants.LANGUAGE
import com.example.requestmanager.utils.Constants.QUERY_APIKEY
import com.example.requestmanager.utils.Constants.QUERY_LANGUAGE
import com.example.requestmanager.utils.Constants.QUERY_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {


    var networkStatus = false
    var backOnline = false


    val readCategoriesType = dataStoreRepository.readCategoriesType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()
    val readWatch = dataStoreRepository.readWatch.asLiveData()

    fun saveCategories(category: String, categoryId: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveCategories(category = category, categoryId = categoryId)
    }

    fun saveWatchType(watch: String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveWatchType(watch)
    }

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_APIKEY] = API_KEY
        queries[QUERY_LANGUAGE] = LANGUAGE
        queries[QUERY_PAGE] = "1"
        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_LONG).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                saveBackOnline(false)
            }
        }
    }

    fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }


}