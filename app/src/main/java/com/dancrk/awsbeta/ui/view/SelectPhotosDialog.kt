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
import androidx.recyclerview.widget.LinearLayoutManager
import com.dancrk.awsbeta.R
import com.dancrk.awsbeta.data.adapter.recycler.RecyclerAdapter
import com.dancrk.awsbeta.databinding.FragmentDialogSelectPhotosBinding
import java.io.InputStream

class SelectPhotosDialog(
    private val onSubmitClickListener: (MutableList<InputStream>,String) -> Unit
) : DialogFragment() {

    private var _binding: FragmentDialogSelectPhotosBinding? = null
    private val binding get() = _binding!!

    private val sliderItems = mutableListOf<Uri>()
    private val imagesStreams = mutableListOf<InputStream>()
    private lateinit var adapter: RecyclerAdapter

    private val loadImageFromGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri = result.data?.data!!
                sliderItems.add(imageUri)
                val stream = context!!.contentResolver.openInputStream(imageUri)!!
                imagesStreams.add(stream)
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

        binding.addImage.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK
            )
            intent.type = "image/*"
            loadImageFromGalleryLauncher.launch(intent)
        }

        binding.sendButton.setOnClickListener {
            val fecha = binding.fechaET.text.toString().replace("/","-")
            val bus = binding.autobusET.text.toString()
            onSubmitClickListener(imagesStreams,fecha+bus)
            dismiss()
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        adapter = RecyclerAdapter(sliderItems, requireContext())
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recycler.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}