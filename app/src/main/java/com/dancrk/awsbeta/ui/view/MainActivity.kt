package com.dancrk.awsbeta.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.dancrk.awsbeta.data.adapter.viewpager.SliderAdapter
import com.dancrk.awsbeta.databinding.ActivityMainBinding
import com.dancrk.awsbeta.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.singIn()

        binding.bajar.setOnClickListener {
            SelectDateAndBusDialog(onSubmitClickListener = { name ->
                viewModel.getImages(name)
                val texto = name.replace("-","/").split("/")
                binding.textoViajeFecha.text = "fecha: ${texto[0]}/${texto[1]}/${texto[2]} bus: "+texto[3]
                binding.textoViajeFecha.visibility = View.VISIBLE
            }).show(supportFragmentManager, "uploadDialogFragment")
        }

        binding.subir.setOnClickListener {
            binding.textoViajeFecha.visibility = View.INVISIBLE
            SelectPhotosDialog(onSubmitClickListener = { images ,name->
                binding.subiendoImagenes.visibility = View.VISIBLE
                viewModel.uploadImages(images,name,this)
            }).show(supportFragmentManager, "addDialogFragment")
        }
        
        viewModel.errorUpload.observe(this){ error ->
            binding.subiendoImagenes.visibility = View.GONE
            if (error){
                Toast.makeText(this, "Error al subir, intente mas tarde", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Imagenes Cargadas", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.listaImagenes.observe(this){ imagenes ->
            setUpViewPager(imagenes as MutableList<URL>)
        }
    }

    private fun setUpViewPager(imagenes:MutableList<URL>) {
        adapter = SliderAdapter(imagenes,this)
        binding.sliderFotos.adapter = adapter

        binding.sliderFotos.clipToPadding = false
        binding.sliderFotos.clipChildren = false
        binding.sliderFotos.offscreenPageLimit = 3
        binding.sliderFotos.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePagerTransformer = CompositePageTransformer()
        compositePagerTransformer.addTransformer(MarginPageTransformer(30))
        compositePagerTransformer.addTransformer{page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        binding.sliderFotos.setPageTransformer(compositePagerTransformer)
        adapter.notifyDataSetChanged()
    }

}