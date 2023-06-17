package com.example.converso.viewModels

import Message
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converso.models.User
import com.example.converso.reposities.AuthRepository
import com.example.converso.reposities.FirestoreRepository
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: FirestoreRepository = FirestoreRepository()
): ViewModel() {

    private val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList

    var messageListener: ListenerRegistration? = null

    private var currentUser: User? = null
    var currentUserId = ""


    init {
    getCurrentProfile()
    }

    private fun getCurrentProfile() = viewModelScope.launch {
        currentUserId = AuthRepository().getUserId()

        if(currentUserId.isNotBlank()){
            repository.getUserProfile(currentUserId){
                currentUser = it
                Log.d("Received user info", it.toString())
            }
        }
    }


    fun sendNewMessage(body: String, chatId: String) = viewModelScope.launch {
        if(body.isNotBlank() && chatId.isNotBlank()){
            var sentMessage= Message(
                message = body,
                from = currentUser?.username ?: "",
                fromUserId = currentUserId,
                fromUserProfilePic = currentUser?.profileImage ?: ""
            )

            repository.addNewMessage(
                newMessage = sentMessage,
                chatId = chatId
            ){
                Log.d("AAA added message success?", it.toString())
            }

        }
    }

    fun getRealtimeMessages(chatId: String){
    Log.d("Start Listening...", chatId)
        val collectionRef = Firebase.firestore
            .collection("conversations")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(50)

        messageListener = collectionRef.addSnapshotListener { snapshot, e ->
            Log.d("AAA listening..", "YES!")
            if (e != null) {
                Log.d("AAA listener went wrong", e.localizedMessage ?: "oops")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                // Converts the result data to our List<Car>
                Log.d("AAA received realtime...", snapshot.toString())
                _messageList.clear()
                for(document in snapshot){
                    _messageList.add(document.toObject(Message::class.java))
                }
            }


        }

    }

    override fun onCleared() {
        Log.d("AAA Stop view model..", "YES!")
        messageListener?.remove()
        messageListener = null
    }


}