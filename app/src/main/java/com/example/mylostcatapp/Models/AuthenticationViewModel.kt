package com.example.mylostcatapp.Models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylostcatapp.REST.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel : ViewModel() {

    private val authenticationRepository: AuthenticationRepository = AuthenticationRepository()
    val userMutableLiveData: MutableLiveData<FirebaseUser> =
        authenticationRepository.userMutableLiveData

    init {

    }

    fun register(email: String, password: String) {
        authenticationRepository.register(email, password)
    }

    fun logIn(email: String, password: String) {
        authenticationRepository.logIn(email, password)
    }

    fun signOut() {
        authenticationRepository.signOut()
    }

}
