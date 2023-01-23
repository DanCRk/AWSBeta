package com.dancrk.awsbeta.data.adapter.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dancrk.awsbeta.databinding.SliderItemContainerBinding

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = SliderItemContainerBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(image: Uri, context: Context) {
        Glide.with(context)
            .load(image)
            .centerCrop()
            .into(binding.containerImageSlider)
    }
}