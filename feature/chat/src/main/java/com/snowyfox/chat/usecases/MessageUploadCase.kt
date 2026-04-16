package com.snowyfox.chat.usecases

import com.snowyfox.chat.data.local.BackupRepository
import javax.inject.Inject

class MessageUploadCase @Inject constructor(
    private val backupRepository: BackupRepository
){
    suspend operator fun invoke(){
        backupRepository.backupAllConversations()
    }
}