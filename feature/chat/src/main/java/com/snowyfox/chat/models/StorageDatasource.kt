package com.snowyfox.chat.models

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

import javax.inject.Inject

class StorageDatasource @Inject constructor(
    private val firebaseStorage: FirebaseFirestore
) {
}