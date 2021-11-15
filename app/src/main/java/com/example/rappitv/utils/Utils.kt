package com.example.rappitv.utils

import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View, message: String) {
    val snackBar = Snackbar.make(
        view,
        message,
        Snackbar.LENGTH_INDEFINITE
    )
    val snackBarView = snackBar.view
    val snackTextView =
        snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

    snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
    snackTextView.maxLines = 4
    snackBar.setAction("Ok") {}
        .show()
}


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }

    })
}