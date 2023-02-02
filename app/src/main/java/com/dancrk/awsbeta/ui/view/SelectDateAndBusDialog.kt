package com.dancrk.awsbeta.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dancrk.awsbeta.R
import com.dancrk.awsbeta.databinding.FragmentDialogSelectPhotosBinding

class SelectDateAndBusDialog(
    private val onSubmitClickListener: (String) -> Unit
) : DialogFragment() {

    private var _binding: FragmentDialogSelectPhotosBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSelectPhotosBinding.inflate(layoutInflater)
        dialog!!.window?.setBackgroundDrawableResource(R.color.transparent)

        binding.recycler.visibility = View.GONE

        binding.addImage.setImageResource(R.drawable.ic_cloud_download)

        binding.sendButton.text = "BAJAR"

        /**
         * cierra el dialogo y envia las imagenes y los datos introducidos
         */
        binding.sendButton.setOnClickListener {
            val fecha = binding.fechaET.text.toString().replace("/", "-")
            val bus = binding.autobusET.text.toString()
            onSubmitClickListener("$fecha-$bus")
            dismiss()
        }
        return binding.root
    }
}