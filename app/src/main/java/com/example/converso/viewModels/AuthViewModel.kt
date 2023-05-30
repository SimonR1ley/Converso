package com.example.converso.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converso.reposities.AuthRepository
import com.example.converso.reposities.FirestoreRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository(),

): ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean get() = repository.hasUser()

    var authUiState by mutableStateOf(AuthUiState())
    private set


    fun handleInputStateChanges(target: String, value: String){
        if(target == "LoginEmail") {
            authUiState = authUiState.copy(loginEmail = value)
        } else if (target == "loginPassword"){
            authUiState = authUiState.copy(loginPassword = value)
        }
        else if (target == "registerUsername"){
            authUiState = authUiState.copy(registerUsername = value)
        }
        else if (target == "registerEmail"){
            authUiState = authUiState.copy(registerEmail = value)
        }
        else if (target == "registerPassword"){
            authUiState = authUiState.copy(registerPassword = value)
        }


    }

    fun createNewUser(context: Context) = viewModelScope.launch {
        authUiState = authUiState.copy(errorMessage ="")
        try{
            if(authUiState.registerUsername.isBlank() || authUiState.registerEmail.isBlank() || authUiState.registerPassword.isBlank()){
                authUiState = authUiState.copy(errorMessage ="Please fill username, email and password")
            } else {
                authUiState = authUiState.copy(isLoading = true)

                repository.registerNewUser(
                    authUiState.registerEmail,
                    authUiState.registerPassword
                ){ userId ->
                    if(userId.isNotBlank()){
                        Log.d("Registering success ", userId.toString())
                        FirestoreRepository().createUserInDatabase(
                            uid = userId,
                            username = authUiState.registerUsername,
                            email = authUiState.registerEmail
                        ){
                            if(it) {
                                Toast.makeText(
                                    context,
                                    "Registering Completed",
                                    Toast.LENGTH_SHORT).show()

                                authUiState = authUiState.copy(authSuccess = true)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT).show()

                                authUiState = authUiState.copy(authSuccess = false)
                            }
                        }

                    } else {
                        Log.d("Error registering ", "Something went wrong")
                        Toast.makeText(context, "Registering Failed", Toast.LENGTH_SHORT).show()
                        authUiState = authUiState.copy(authSuccess = false)
                        authUiState = authUiState.copy(errorMessage ="Invalid Email/Password")
                    }
                }
            }
        }catch(e:Exception){
            Log.d("Error Registering: ", e.localizedMessage)
            e.printStackTrace()
        }finally{
            authUiState = authUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        authUiState = authUiState.copy(errorMessage ="")
        try{
            if(authUiState.loginEmail.isBlank() || authUiState.loginPassword.isBlank()){
                authUiState = authUiState.copy(errorMessage ="Please fill username, email and password")
            } else {
                authUiState = authUiState.copy(isLoading = true)

                repository.loginUser(
                    authUiState.loginEmail,
                    authUiState.loginPassword
                ){ isCompleted ->
                    if(isCompleted){
                        Log.d("Login success ", "Yay")

                        Toast.makeText(context, "Registering Completed", Toast.LENGTH_SHORT).show()
                        authUiState = authUiState.copy(authSuccess = true)

                    } else {
                        Log.d("Error registering ", "Something went wrong")
                        Toast.makeText(context, "Registering Failed", Toast.LENGTH_SHORT).show()
                        authUiState = authUiState.copy(authSuccess = false)
                        authUiState = authUiState.copy(errorMessage ="Invalid Email/Password")
                    }
                }
            }
        }catch(e:Exception){
            Log.d("Error Registering: ", e.localizedMessage)
            e.printStackTrace()
        }finally{
            authUiState = authUiState.copy(isLoading = false)
        }
    }

}

data class AuthUiState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authSuccess: Boolean = false,

    val loginEmail: String = "",
    val loginPassword: String = "",

    val registerUsername: String = "",
    val registerEmail: String = "",
    val registerPassword: String = "",

        )