package com.example.converso.viewModels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converso.models.User
import com.example.converso.reposities.AuthRepository
import com.example.converso.reposities.FirestoreRepository
import com.example.converso.reposities.StorageRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val firestoreRepository: FirestoreRepository = FirestoreRepository(),
    private val storageRepository: StorageRepository = StorageRepository()
    ): ViewModel() {

    private val hasUser = authRepository.hasUser()
    private val currentUserId = authRepository.getUserId()

    init {
       Log.d("Profile View Model", "INIT")
       getProfileData()
    }


    var profileUiState by mutableStateOf(ProfileUiState())
        private set

    var oldImage: String = ""

    fun handleUsernameStateChanges(value: String){
        profileUiState = profileUiState.copy(username = value)
    }

    fun handleProfileImageChange(value: Uri){
        profileUiState = profileUiState.copy(profileImage = value)
    }

//    private fun getProfileData() = viewModelScope.launch {
//
//
//        if(currentUserId.isNotBlank()){
//            firestoreRepository.getUserProfile(currentUserId){
//                profileUiState = profileUiState.copy(
//                    username = it?.username ?: "",
//                    email = it?.email ?: "",
//                    profileImage = Uri.parse(it?.profileImage),
//                )
//                oldImage = it?.profileImage ?: ""
//                Log.d("Received user info", it.toString())
//            }
//        }
//    }


    private fun getProfileData() = viewModelScope.launch {
        if (currentUserId.isNotBlank()) {
            firestoreRepository.getUserProfile(currentUserId) { userProfile ->
                val profileImageUri = userProfile?.profileImage?.let { Uri.parse(it) } ?: Uri.EMPTY
                profileUiState = profileUiState.copy(
                    username = userProfile?.username ?: "",
                    email = userProfile?.email ?: "",
                    profileImage = profileImageUri
                )
                oldImage = userProfile?.profileImage ?: ""
                Log.d("Received user info", userProfile.toString())
            }
        }
    }



    fun saveProfileData() = viewModelScope.launch {
        if(hasUser){

            var downloadUrl = oldImage

            if(oldImage != profileUiState.profileImage.toString() || oldImage.isBlank()){

                storageRepository.uploadImageToStorage(
                    imageUri = profileUiState.profileImage,
                    fileName = "$currentUserId=${profileUiState.username}"
                ){
                    downloadUrl = it
                }
            }
            firestoreRepository.updateProfileInformation(
                user = User(
                    id = currentUserId,
                    username = profileUiState.username,
                    email = profileUiState.email,
                    profileImage = downloadUrl
                )
            ){
                Log.d("AAA Updated User ", it.toString())
            }
        }
    }


    }

data class ProfileUiState (

    val username: String = "",
    val email: String = "",
    val profileImage: Uri = Uri.EMPTY,
    )