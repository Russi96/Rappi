package com.example.rappitv.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val moviesVIdeo: MoviesVIdeo,
    val imageBackground: String
): Parcelable