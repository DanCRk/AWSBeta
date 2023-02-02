package com.dancrk.awsbeta.data.network

import android.util.Log
import com.amplifyframework.kotlin.core.Amplify
import com.amplifyframework.storage.StorageException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class StorageBucket @Inject constructor(){

    /**
     * obtiene las imagenes del bucket de aws
     * @param nombre nombre de la carpeta a buscar las imagenes
     * @return lista con las URL de las imagenes
     */
    suspend fun getImagesFromAWS(nombre:String):MutableList<URL>{
        val imagenes: MutableList<URL> = mutableListOf()
        var numero =0
        try {
            Amplify.Storage.list("imagesApp/$nombre").items.forEach {
                numero++
                val url = Amplify.Storage.getUrl("imagesApp/$nombre/$numero.jpeg").url
                imagenes.add(url)
                Log.i("MyAmplifyAppDownload", "Successfully generated: $url")
                Log.i("MyAmplifyAppDownload", "Item: ${it.key}")
            }
        } catch (error: StorageException) {
            Log.e("MyAmplifyAppDownload", "List failure", error)
        }
        return imagenes
    }

    /**
     * Carga las imagenes al bucket de aws en la carpeta con el nombre pasado, y estas enumeradas del 1 al n donde
     * n es el tamaño de la lista de imagenes
     *
     * @param imagenes lista de inputStream con las imagenes a cargar en el bucket
     * @param nombre nombre de la carpeta donde se guardaran las imagenes
     * @return falso si hubo un error al cargar alguna de las imagenes, true si todas se cargaron
     */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    suspend fun uploadImagesToAWS(imagenes: MutableList<InputStream>, nombre:String):Boolean{
        val error: Boolean
        var numero =1
        for (image in imagenes){
            val upload = Amplify.Storage.uploadInputStream("imagesApp/$nombre/$numero.jpeg", image)
            try {
                val result = upload.result()
                Log.i("MyAmplifyApp", "Successfully uploaded: ${result.key}")
                numero++
            } catch (error: StorageException) {
                Log.e("MyAmplifyApp", "Upload failed", error)
            }
        }
        error = numero != imagenes.size

        return error
    }

}