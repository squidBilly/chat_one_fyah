package com.snowyfox.chat.network.datasource

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseFirestoreProvider(private val context: Context) {
    fun getFirebaseFirestore(): FirebaseFirestore {
        FirebaseApp.initializeApp(context)
        return FirebaseFirestore.getInstance()
    }

}