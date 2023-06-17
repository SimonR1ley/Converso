package com.example.converso.reposities

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class StorageRepository {

    val storageRef = Firebase.storage.reference
        suspend fun uploadImageToStorage(
        imageUri: Uri,
        fileName: String,
        onSuccess: (downloadUrl: String) -> Unit
        ){
            try{

                val downloadUrl = storageRef.child("profiles/fileName")
                    .putFile(imageUri).await()
                    .storage.downloadUrl.await()

                onSuccess(downloadUrl.toString())

            }catch(e: Exception){
                Log.d("AA Something went wrong with upload", e.localizedMessage)
                e.printStackTrace()
                onSuccess("")
            }

    }
}