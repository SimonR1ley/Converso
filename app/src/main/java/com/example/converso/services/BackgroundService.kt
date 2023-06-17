package com.example.converso.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.converso.reposities.AuthRepository
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class BackgroundService: Service(){

    var onMessageListener: ListenerRegistration? = null

    var authRepository = AuthRepository()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread{
            while(true){
                try{

                    Log.d("AAA service", "running")

                    if(authRepository.hasUser()){
                        Log.d("service", "user logged in")

                        if(onMessageListener == null){
                            startirestoreListener()
                        } else {
                            Log.e("service", "already listening")
                        }
                    } else {
                        Log.e("service", "user not logged in")
                    }

                    Thread.sleep(15000)
                }catch(e: Exception){
                    onMessageListener = null
                    e.printStackTrace()
                }
            }
        }.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("AA service", "Destroyed")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        onMessageListener = null
        TODO("Not yet implemented")
    }

    private fun startirestoreListener(){
        Log.d("Start Listening...", "yes")
        val collectionRef = Firebase.firestore
            .collectionGroup("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(10)

        onMessageListener = collectionRef.addSnapshotListener { snapshot, e ->
            Log.d("AAA listening..", "YES!")
            if (e != null) {
                Log.d("AAA listener went wrong", e.localizedMessage ?: "oops")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                // Converts the result data to our List<Car>
                Log.d("AAA received realtime...", snapshot.toString())

                for(dc in snapshot.documentChanges){
                    when (dc.type) {
                        DocumentChange.Type.ADDED ->
                            if (authRepository.currentUser?.uid == dc.document.data["fromUserId"].toString()) {
                                MyNotification(
                                    this,
                                    "New Message",
                                    dc.document.data["message"].toString()
                                ).showNotification()}
                                DocumentChange.Type.MODIFIED -> Log.d("AA", "${dc.document.data}")
                                DocumentChange.Type.REMOVED -> Log.d("AA", "${dc.document.data}")
                            }

                }

            }
        }

    }
}