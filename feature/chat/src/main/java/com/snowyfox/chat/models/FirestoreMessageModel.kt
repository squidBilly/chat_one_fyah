package com.snowyfox.chat.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import com.snowyfox.chat.data.model.Messages
import java.text.SimpleDateFormat
import java.util.Locale

data class FirestoreMessageModel(
    @Transient
    val id: String = "",
    @get:PropertyName("senderId")
    @set:PropertyName("senderId")
    var senderId: String = "",
    @get:PropertyName("senderName")
    @set:PropertyName("senderName")
    var senderName: String = "",
    @get:PropertyName("senderAvatar")
    @set:PropertyName("senderAvatar")
    var senderAvatar: String = "",
    @get:PropertyName("content")
    @set:PropertyName("content")
    var content: String = "",
    @get:PropertyName("timestamp")
    @set:PropertyName("timestamp")
    var timestamp: Timestamp = Timestamp.now()
){
    fun toDomain(userId: String): Messages {
        return Messages(
            id = id,
            senderName = senderName,
            senderAvatar = senderAvatar,
            isMine = userId == senderId,
            contentType = Messages.ContentType.TEXT,
            content = content,
            contentDescription = "",
            timestamp = timestamp.toDateString()
        )
    }
    companion object {
        fun fromDomain(messages: Messages): FirestoreMessageModel {
            return FirestoreMessageModel(
                id = "",
                senderName = messages.senderName,
                senderAvatar = messages.senderAvatar,
                content = messages.content
            )
        }
    }
}

/***
 * Create a SimpleDateFormat instance with  the desired formant and the default locale
 * */
private fun Timestamp.toDateString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val date = this.toDate()
    return formatter.format(date)
}
