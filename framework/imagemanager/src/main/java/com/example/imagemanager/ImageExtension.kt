package com.example.imagemanager

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory



fun ImageView.bindImageUrl(url: String?, @DrawableRes placeholder: Int,
                           @DrawableRes errorPlaceholder: Int) {
    if (url.isNullOrBlank()) {
        setImageResource(placeholder)
        return
    }
    
    Glide.with(context)
        .load(url)
        .error(errorPlaceholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(placeholder)
        .into(this)
}

fun ImageView.bindImageUrlLoad(url: String?) {

    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    Glide.with(context)
        .load(url)
        .transition(withCrossFade(factory))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}