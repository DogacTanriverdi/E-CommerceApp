package com.dogactanriverdi.e_commerceapp.common

import android.widget.ImageView
import com.bumptech.glide.Glide

    fun ImageView.loadImage(uri: String) {
        Glide.with(this)
            .load(uri)
            .into(this)
    }