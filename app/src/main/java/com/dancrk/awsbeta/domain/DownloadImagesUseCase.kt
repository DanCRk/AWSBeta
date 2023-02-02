package com.dancrk.awsbeta.domain

import com.dancrk.awsbeta.data.Repository
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class DownloadImagesUseCase @Inject constructor(private val repository: Repository)  {

    /**
     * Llama al repositorio por las imagenes del bucket
     */
    suspend operator fun invoke(nombre:String):MutableList<URL>{
        return  repository.getImagesFromAWS(nombre)
    }
}