package com.example.inappmessaging.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingUtil {


    @JvmStatic
    @BindingAdapter("image")
    fun image(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view).load(url)
                .apply(RequestOptions())
                .into(view)
        }
    }

}