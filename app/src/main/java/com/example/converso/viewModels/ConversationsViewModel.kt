package com.example.converso.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converso.models.Conversation
import com.example.converso.reposities.FirestoreRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ConversationsViewModel(
    private val repository: FirestoreRepository = FirestoreRepository()
):ViewModel() {



    private val _convoLists = mutableStateListOf<Conversation>()
    val convoList: List<Conversation> = _convoLists


    init{
        getConversations()
    }

   private fun getConversations() = viewModelScope.launch {
        repository.getAllConversations() {data ->
            if(data != null){
                for(document in data){
                    _convoLists.add(document)
                }
            }

        }
    }

}