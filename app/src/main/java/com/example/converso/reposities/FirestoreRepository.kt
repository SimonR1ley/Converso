package com.example.converso.reposities

import Message
import android.content.ContentValues
import android.util.Log
import com.example.converso.models.Conversation
import com.example.converso.models.User
import com.google.firebase.firestore.SetOptions
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

        val conversations = arrayListOf<Conversation>()

    conversationRef.orderBy("title").get()
        .addOnSuccessListener {

            for(document in it){
                conversations.add(
                    Conversation(
                        id = document.id,
                        title = document.data["title"].toString(),
                        image = document.data["image"].toString()
                    )
                )
            }
            Log.d("Conversation Data", conversations.toString())
            onSuccess(conversations)
        }
        .addOnFailureListener{
            Log.d("Trying to retrieve daya", it.localizedMessage )
            onSuccess(null)
        }.await()
    }

    suspend fun addNewMessage(
        newMessage: Message,
        chatId: String,
        onSuccess: (Boolean) -> Unit
    ) {
        conversationRef.document(chatId).collection("messages")
            .add(newMessage)
            .addOnSuccessListener {
                Log.d("AAA new message added..", it.id)
                onSuccess.invoke(true)
            }
            .addOnFailureListener {
                Log.d("AAA problem adding message: ", it.localizedMessage)
                it.printStackTrace()
                onSuccess.invoke(false)
            }.await()
    }

    suspend fun getUserProfile(
        uid: String,
        onSuccess: (User?) -> Unit
    ) {
        Log.d("AAA getting user: ", uid)
        userRef.document(uid).get()
            .addOnSuccessListener {
                if (it != null) {
                    Log.d(ContentValues.TAG, "AA DocumentSnapshot data: ${it?.data}")
                    onSuccess.invoke(it?.toObject(User::class.java))
                } else { Log.d(ContentValues.TAG, "AA No such document"); onSuccess.invoke(null)
                } }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "AA get failed with ", exception)
                onSuccess.invoke(null)
            }.await()
    }


    suspend fun updateProfileInformation(
        user: User,
        onSuccess: (Boolean) -> Unit
    ){
        userRef.document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Updated User: ", "Yay")
                onSuccess.invoke(true)
            }
            .addOnFailureListener{
                Log.d("Failed to update User", it.localizedMessage)
                onSuccess.invoke(false)
            }.await()
    }


}