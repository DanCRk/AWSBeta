package com.dancrk.awsbeta.domain

import com.dancrk.awsbeta.data.Repository
import java.io.InputStream
import javax.inject.Inject

class UploadImagesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(imagenes: MutableList<InputStream>, nombre: String): Boolean {
        return repository.uploadImagesToAWS(imagenes, nombre)
    }

}