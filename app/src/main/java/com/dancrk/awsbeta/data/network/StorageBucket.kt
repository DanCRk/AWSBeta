package com.dancrk.awsbeta.data.network

import android.util.Log
import com.amplifyframework.kotlin.core.Amplify
import com.amplifyframework.storage.StorageException
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

class StorageBucket @Inject constructor(){

    suspend fun getImagesFromAWS(nombre:String):MutableList<URL>{
        val imagenes: MutableList<URL> = mutableListOf()
        var numero =0
        while (true){
            try {
                numero++
                val url = Amplify.Storage.getUrl("imagesApp/$nombre-$numero.png").url
                imagenes.add(url)
                Log.i("MyAmplifyApp", "Successfully generated: $url")
            } catch (error: StorageException) {
                Log.e("MyAmplifyApp", "URL generation failure", error)
                break
            }
        }
        return imagenes
    }

    suspend fun uploadImagesToAWS(imagenes: MutableList<InputStream>,nombre:String):Boolean{
        val error: Boolean
        var numero =0
        for (image in imagenes){
            numero++
            val upload = Amplify.Storage.uploadInputStream("imagesApp/$nombre-$numero.png", image)
            try {
                val result = upload.result()
                Log.i("MyAmplifyApp", "Successfully uploaded: ${result.key}")
            } catch (error: StorageException) {
                Log.e("MyAmplifyApp", "Upload failed", error)
            }
        }
        error = numero != imagenes.size

        return error
    }

}