package com.dancrk.awsbeta.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import com.dancrk.awsbeta.data.Repository
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

class UploadImagesUseCase @Inject constructor(private val repository: Repository) {

    /**
     * Convierte los Uri de las imagenes a inputStream comprimidos a un 65% de calidad y las manda al repositorio
     *
     * @param imagenes lista con los Uri de las imagenes a subir
     * @param name nombre del archivo a subir
     * @param context contexto de la actividad o del que viene
     *
     * @return true si se subieron bien las imagenes, false si hubo un error
     */
    suspend operator fun invoke(imagenes: MutableList<Uri>, nombre: String,context: Context): Boolean {
        val inputStreamImages:MutableList<InputStream> = mutableListOf()
        for (image in imagenes){
            val source = ImageDecoder.createSource(context.contentResolver, image)
            val bitmap = ImageDecoder.decodeBitmap(source)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,65,baos)
            val imageCompress = ByteArrayInputStream(baos.toByteArray())
            inputStreamImages.add(imageCompress)
        }
        return repository.uploadImagesToAWS(inputStreamImages, nombre)
    }

}