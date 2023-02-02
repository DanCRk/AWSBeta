package com.dancrk.awsbeta.ui.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dancrk.awsbeta.R
import com.dancrk.awsbeta.data.adapter.recycler.RecyclerAdapter
import com.dancrk.awsbeta.databinding.FragmentDialogSelectPhotosBinding
import kotlinx.coroutines.launch
import java.io.InputStream

class SelectPhotosDialog(
    private val onSubmitClickListener: (MutableList<Uri>,String) -> Unit
) : DialogFragment() {

    private var _binding: FragmentDialogSelectPhotosBinding? = null
    private val binding get() = _binding!!

    private val sliderItems = mutableListOf<Uri>()
    private lateinit var adapter: RecyclerAdapter

    /**
     * Registra cuando se regresa de la galeria y recoge los datos de la imagen que trajo
     */
    private val loadImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri = result.data?.data!!
                sliderItems.add(imageUri)
                adapter.notifyDataSetChanged()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSelectPhotosBinding.inflate(layoutInflater)
        dialog!!.window?.setBackgroundDrawableResource(R.color.transparent)

        setUpRecyclerView()

        /**
         * crea el intent para ir a la galeria y lo lanza
         */
        binding.addImage.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK
            )
            intent.type = "image/*"
            loadImageFromGalleryLauncher.launch(intent)
        }

        /**
         * cierra el dialogo y envia las imagenes y los datos introducidos
         */
        binding.sendButton.setOnClickListener {
            val fecha = binding.fechaET.text.toString().replace("/","-")
            val bus = binding.autobusET.text.toString()
            onSubmitClickListener(sliderItems,"$fecha-$bus")
            dismiss()
        }
        return binding.root
    }

    /**
     * Inicializa el recyclerview pasandole los parametros requeridos
     */
    private fun setUpRecyclerView() {
        adapter = RecyclerAdapter(sliderItems, requireContext())
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}