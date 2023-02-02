package com.dancrk.awsbeta.data

import com.dancrk.awsbeta.data.network.StorageBucket
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class Repository @Inject constructor(
    private val storageBucket: StorageBucket
) {

    /**
     * llama al storage y recupera la lista de imagenes
     * @param nombre nombre de la carpeta a buscar las imagenes
     * @return lista con las URL de las imagenes recuperadas del bucket
     */
    suspend fun getImagesFromAWS(nombre:String):MutableList<URL>{
        return storageBucket.getImagesFromAWS(nombre)
    }

    /**
     * llama al storage y le pasa la lista de imagenes a subir
     * @param imagenes lista de inputStream con las imagenes a cargar en el bucket
     * @param nombre nombre de la carpeta donde se guardaran las imagenes
     */
    suspend fun uploadImagesToAWS(imagenes: MutableList<InputStream>, nombre:String):Boolean{
        return storageBucket.uploadImagesToAWS(imagenes,nombre)
    }
}