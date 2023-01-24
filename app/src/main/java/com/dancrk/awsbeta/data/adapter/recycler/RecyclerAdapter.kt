package com.dancrk.awsbeta.data.adapter.recycler

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dancrk.awsbeta.R

class RecyclerAdapter(val images:List<Uri>, val contexto: Context) : RecyclerView.Adapter<ViewHolderRecycler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRecycler {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderRecycler(layoutInflater.inflate(R.layout.recycler_item_container, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderRecycler, position: Int) {
        holder.bind(images[position],contexto)
    }

    override fun getItemCount(): Int = images.size

}