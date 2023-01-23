package com.dancrk.awsbeta.domain

import com.dancrk.awsbeta.data.Repository
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class DownloadImagesUseCase @Inject constructor(private val repository: Repository)  {

    suspend operator fun invoke(nombre:String):MutableList<URL>{
        return  repository.getImagesFromAWS(nombre)
    }
}