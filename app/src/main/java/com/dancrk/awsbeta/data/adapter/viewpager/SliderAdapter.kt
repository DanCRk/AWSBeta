package com.dancrk.awsbeta.data.adapter.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dancrk.awsbeta.R
import java.net.URL

class SliderAdapter internal constructor(
    sliderItem: MutableList<URL>,
    val contexto: Context
): RecyclerView.Adapter<ViewHolderSlider>(){

    private val sliderItems: List<URL>

    init {
        this.sliderItems = sliderItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSlider =
        ViewHolderSlider(LayoutInflater.from(parent.context).inflate(R.layout.slider_item_container, parent, false))

    override fun getItemCount(): Int = sliderItems.size

    override fun onBindViewHolder(holder: ViewHolderSlider, position: Int) {
        holder.bind(sliderItems[position],contexto)
    }
}