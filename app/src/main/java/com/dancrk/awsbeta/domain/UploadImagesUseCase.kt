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

    suspend operator fun invoke(imagenes: MutableList<Uri>, nombre: String,context: Context): Boolean {
        val inputStreamImages:MutableList<InputStream> = mutableListOf()
        for (image in imagenes){
            val source = ImageDecoder.createSource(context.contentResolver, image)
            val bitmap = ImageDecoder.decodeBitmap(source)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,65,baos)
            val imageCompress = ByteArrayInputStream(baos.toByteArray())
//            val imageCompress = context.contentResolver.openInputStream(image)!!
            inputStreamImages.add(imageCompress)
        }
        return repository.uploadImagesToAWS(inputStreamImages, nombre)
    }

}