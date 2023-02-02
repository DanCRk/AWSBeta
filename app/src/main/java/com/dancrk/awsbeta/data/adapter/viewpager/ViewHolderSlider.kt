package com.dancrk.awsbeta.data.adapter.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dancrk.awsbeta.databinding.RecyclerItemContainerBinding
import com.dancrk.awsbeta.databinding.SliderItemContainerBinding
import java.net.URL

class ViewHolderSlider(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = SliderItemContainerBinding.bind(view)

    /**
     * Carga la imagen en el image view utilizando glide
     */
    @SuppressLint("SetTextI18n")
    fun bind(image: URL, context: Context) {
        Glide.with(context)
            .load(image)
            .centerCrop()
            .into(binding.containerImageSlider)
    }
}