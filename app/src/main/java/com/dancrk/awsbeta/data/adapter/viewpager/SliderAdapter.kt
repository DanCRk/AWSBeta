package com.dancrk.awsbeta.data.adapter.viewpager

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dancrk.awsbeta.R
import java.io.InputStream
import java.net.URL

class SliderAdapter internal constructor(
    sliderItem: MutableList<String>,
    val contexto: Context
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>(){

    private val sliderItems: List<String>

    init {
        this.sliderItems = sliderItem
    }

    class SliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView = itemView.findViewById<ImageView>(R.id.containerImageSlider)

        fun image(sliderItem: String,context: Context){
            Glide.with(context)
                .load(sliderItem)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder =
        SliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_item_container, parent, false))

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(sliderItems[position],contexto)
    }

    override fun getItemCount(): Int = sliderItems.size
}