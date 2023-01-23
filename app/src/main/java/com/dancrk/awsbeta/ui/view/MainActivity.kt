package com.dancrk.awsbeta.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.dancrk.awsbeta.databinding.ActivityMainBinding
import com.dancrk.awsbeta.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.singIn()

        binding.bajar.setOnClickListener {
            
        }

        binding.subir.setOnClickListener {
            SelectPhotosDialog(onSubmitClickListener = { images ,name->
                viewModel.uploadImages(images,name)
            }).show(supportFragmentManager, "addDialogFragment")
        }
        
        viewModel.error.observe(this){ error ->
            if (error){
                Toast.makeText(this, "Error al subir", Toast.LENGTH_SHORT).show()   
            }else{
                Toast.makeText(this, "Subido satisfactoriamente", Toast.LENGTH_SHORT).show()
            }
            
        }

    }
}