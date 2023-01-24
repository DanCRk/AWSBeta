package com.dancrk.awsbeta.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.BuildConfig
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.core.Amplify
import com.dancrk.awsbeta.domain.DownloadImagesUseCase
import com.dancrk.awsbeta.domain.UploadImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uploadImagesUseCase: UploadImagesUseCase,
    private val downloadImagesUseCase: DownloadImagesUseCase
) : ViewModel() {

    val listaImagenes = MutableLiveData<List<URL>>()
    val errorUpload = MutableLiveData<Boolean>()

    fun uploadImages(imagenes: MutableList<InputStream>, name: String) {
        viewModelScope.launch {
            val response = uploadImagesUseCase(imagenes,name)
            if (response){
                errorUpload.postValue(true)
            }else{
                errorUpload.postValue(false)
            }
        }
    }

    fun getImages(nombre: String) {
        viewModelScope.launch {
            val response = downloadImagesUseCase(nombre)
            if (response.isNotEmpty()) {
                listaImagenes.postValue(response)
            }
        }
    }

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