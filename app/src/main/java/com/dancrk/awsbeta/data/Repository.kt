package com.dancrk.awsbeta.data

import com.dancrk.awsbeta.data.network.StorageBucket
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class Repository @Inject constructor(
    private val storageBucket: StorageBucket
) {

    suspend fun getImagesFromAWS(nombre:String):MutableList<URL>{
        return storageBucket.getImagesFromAWS(nombre)
    }

    suspend fun uploadImagesToAWS(imagenes: MutableList<InputStream>, nombre:String):Boolean{
        return storageBucket.uploadImagesToAWS(imagenes,nombre)
    }
}