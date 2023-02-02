package com.dancrk.awsbeta.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify
import com.dancrk.awsbeta.domain.DownloadImagesUseCase
import com.dancrk.awsbeta.domain.UploadImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val downloadImagesUseCase: DownloadImagesUseCase
) : ViewModel() {

    val listaImagenes = MutableLiveData<List<URL>>()
    val errorUpload = MutableLiveData<Boolean>()

    /**
     * Manda las Uris a la capa de datos y realiza la logica por si hubo un error al subir las imagenes
     * y postea el valor a la variable errorUpload
     *
     * @param imagenes lista con los Uri de las imagenes a subir
     * @param name nombre del archivo a subir
     * @param context contexto de la actividad o del que viene
     */
    fun uploadImages(imagenes: MutableList<Uri>, name: String,context: Context) {
        viewModelScope.launch {
            val response = uploadImagesUseCase(imagenes,name,context)
            if (response){
                errorUpload.postValue(true)
            }else{
                errorUpload.postValue(false)
            }
        }
    }

    /**
     * Recoge las URL de la capa de datos y realiza la logica por si hubo un error al descargar las URL
     *
     * @param nombre nombre de los archivos a consultar
     */
    fun getImages(nombre: String) {
        viewModelScope.launch {
            val response = downloadImagesUseCase(nombre)
            if (response.isNotEmpty()) {
                listaImagenes.postValue(response)
            }
        }
    }

    /**
     * Crea un usuario en la pool de usuarios de cognito, se requiere correo, usuario y contraseña
     */
    fun singUp(){
        viewModelScope.launch {
            val options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "dancrkyt@gmail.com")
                .build()
            try {
                val result = Amplify.Auth.signUp("daniel", "cdam2001", options)
                Log.i("AuthQuickStart", "Result: $result")
            } catch (error: AuthException) {
                error.stackTrace
                Log.e("AuthQuickStart", "Sign up failed", error)
            }
        }
    }

    /**
     * Envia el codigo de verificacion que llega al correo, se requiere el usuario y el codigo
     */
    fun code(){
        viewModelScope.launch {
            try {
                val code = "068530"
                val result = Amplify.Auth.confirmSignUp("daniel", code)
                if (result.isSignUpComplete) {
                    Log.i("AuthQuickstart", "Signup confirmed")
                } else {
                    Log.i("AuthQuickstart", "Signup confirmation not yet complete")
                }
            } catch (error: AuthException) {
                error.stackTrace
                Log.e("AuthQuickstart", "Failed to confirm signup", error)
            }
        }
    }

    /**
     * Inicia sesion a la cuenta creada de cognito, requiere usuario y contraseña
     */
    fun singIn() {
        viewModelScope.launch {
            try {
                val result = Amplify.Auth.signIn("daniel", "cdam2001")
                if (result.isSignedIn) {
                    Log.i("AuthQuickstart", "Sign in succeeded")
                } else {
                    Log.e("AuthQuickstart", "Sign in not complete")
                }
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Sign in failed", error)
            }
        }
    }

}