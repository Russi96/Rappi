package com.example.rappitv.domain


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesVIdeo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
): Parcelable