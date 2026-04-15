package com.snowyfox.chat.network.datasource

import com.google.firebase.firestore.Query
import com.snowyfox.chat.data.model.Messages
import com.snowyfox.chat.models.FirestoreMessageModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreMessagesDatasource @Inject constructor(
    private val firestore: FirebaseFirestoreProvider
) {
    fun getMessages(chatId: String, userId: String): Flow<Messages> = callbackFlow {
        val chatRef = firestore
            .getFirebaseFirestore()
            .collection("chatId")
            .document(chatId)
            .collection("messages")
        val query = chatRef.orderBy("timestamp", Query.Direction.ASCENDING)
        val listenerRegistration = query.addSnapshotListener { snapshots, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            val messages = snapshots?.documents?.mapNotNull { documentSnapshot ->
                val message = documentSnapshot.toObject(FirestoreMessageModel::class.java)
                message?.copy(id = documentSnapshot.id)
            } ?: emptyList()
            val domainMessage = messages.map { it.toDomain(userId) }
            domainMessage.forEach {
                try {
                    trySend(it).isSuccess
                } catch (e: Exception) {
                    close(e)
                }
            }
        }
        awaitClose { listenerRegistration.remove() }
    }

    fun sendMessage(chatId: String, messages: Messages) {
        val chatRef = firestore
            .getFirebaseFirestore()
            .collection("chats")
            .document(chatId)
            .collection("messages")
        chatRef.add(FirestoreMessageModel.fromDomain(messages))
    }
}