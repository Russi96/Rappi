package com.example.rappitv.moviesmodule.view.fragment.movies.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.rappitv.databinding.FragmentMoviesBottomSheetBinding
import com.example.rappitv.viewmodel.MainViewModel
import com.example.requestmanager.utils.Constants.DEFAULT_CATEGORIES
import com.example.requestmanager.utils.Constants.DEFAULT_CATEGORIES_ID
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.*

@AndroidEntryPoint
class MoviesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var mBinding: FragmentMoviesBottomSheetBinding
    private val mainViewModel by viewModels<MainViewModel>()
    var categoriesType = DEFAULT_CATEGORIES
    var categoriesTypeId = DEFAULT_CATEGORIES_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentMoviesBottomSheetBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.readCategoriesType.asLiveData().observe(viewLifecycleOwner, { response ->
            categoriesType = response.selectedCategory
            updateChip(response.selectedCategoryId, mBinding.chipGroupMoviesBottomSheet)
        })

        mBinding.chipGroupMoviesBottomSheet.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedCategoriesType = chip.text.toString().lowercase(Locale.ROOT)
            categoriesType = selectedCategoriesType
            categoriesTypeId = checkedId
        }

        mBinding.btCategoriesMoviesBottomSheet.setOnClickListener {
            mainViewModel.saveCategories(category = categoriesType, categoryId = categoriesTypeId)
            val action = MoviesBottomSheetDirections.actionMoviesBottomSheetToMoviesFragment(true)
            findNavController().navigate(action)
        }
    }


    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }
}