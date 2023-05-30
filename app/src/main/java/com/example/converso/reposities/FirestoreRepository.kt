package com.example.converso.reposities

import android.util.Log
import com.example.converso.models.Conversation
import com.example.converso.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

const val USER_REF  = "users"
const val CONVERSATION_REF = "conversations"

class FirestoreRepository {

    val db = Firebase.firestore
    private val userRef = db.collection(USER_REF)
    private val conversationRef = db.collection(CONVERSATION_REF)

    fun createUserInDatabase(
                uid: String,
                username: String,
                email: String,
                onSuccess: (Boolean) -> Unit
            ){
    userRef.document(uid)
        .set(
            User(
                id = uid,
                username = username,
                email = email,
                profileImage = "")
        )
        .addOnSuccessListener {
            Log.d("Created User: ", "Yay")
            onSuccess.invoke(true)
        }
        .addOnFailureListener{
            Log.d("Failed to create User", it.localizedMessage)
            onSuccess.invoke(false)
        }
            }


    suspend fun getAllConversations(
        onSuccess: (List<Conversation>?) -> Unit
    ) {
    conversationRef.orderBy("title").get()
        .addOnSuccessListener {
            Log.d("Conversation Data", it.toString())
            onSuccess.invoke(it.toObjects(Conversation::class.java))
        }
        .addOnFailureListener{
            Log.d("Trying to retrieve daya", it.localizedMessage )
            onSuccess(null)
        }.await()
    }



}