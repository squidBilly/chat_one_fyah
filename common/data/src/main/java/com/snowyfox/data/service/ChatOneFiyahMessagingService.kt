package com.snowyfox.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.snowyfox.data.R
import com.snowyfox.framework.navigation.DeepLinks

class ChatOneFiyahMessagingService : FirebaseMessagingService() {
    companion object {
        const val CHANNEL_ID = "Chat_message"
        const val CHANNEL_DESCRIPTION = "Receive a notification when a chat message is received"
        const val CHANNEL_TITLE = "New chat message notification"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val senderName = remoteMessage.data["senderName"]
            val messageContent = remoteMessage.data["message"]
            val chatId = remoteMessage.data["chatId"]
            val messageId = remoteMessage.data["messageId"]

            if (chatId != null && messageId != null) {
                showNotification(senderName, messageId, messageContent, chatId)
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }
    private fun sendRegistrationToServer(token: String){

    }


    private fun showNotification(
        senderName: String?,
        messageId: String,
        messageContent: String?,
        chatId: String
    ) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_TITLE,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESCRIPTION
        }
        notificationManager.createNotificationChannel(channel)
        val deepLinkUrl = DeepLinks.chatRoute.replace("{chatId}", chatId)
        val intent = Intent(Intent.ACTION_VIEW, deepLinkUrl.toUri()).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_circle_notifications)
            .setContentTitle(senderName)
            .setContentText(messageContent)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(messageId.toInt(), notification)

    }
}