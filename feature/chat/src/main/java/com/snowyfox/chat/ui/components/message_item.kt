package com.snowyfox.chat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.snowyfox.chat.models.ChatMessage
import com.snowyfox.chat.models.MessageContent
import com.snowyfox.framework.avatars.Avatar

@Composable
fun MessageItem(message: ChatMessage, modifier: Modifier = Modifier){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if  (message.isMine) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isMine){
            Avatar(
                imageUrl = message.senderAvatar,
                size = 40.dp,
                contentDescription = "${message.senderName}'s avatar"
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            if (message.isMine){
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Text(
                    text = message.senderName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            when( val content = message.messageContent){
                is MessageContent.TextMessage -> {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if(message.isMine) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.secondary
                    ) {
                        Text(
                            text = content.message,
                            color = if (message.isMine) MaterialTheme.colorScheme.onPrimary else Color.White
                        )
                    }
                }
                is MessageContent.ImageMessage -> {
                    AsyncImage(
                        model = content.imageUrl,
                        contentDescription = content.contentDescription,
                        modifier = Modifier.size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = message.timestamp,
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}