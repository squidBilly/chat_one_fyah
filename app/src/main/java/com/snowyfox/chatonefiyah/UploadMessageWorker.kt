package com.snowyfox.chatonefiyah

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.snowyfox.chat.usecases.MessageUploadCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class UploadMessageWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val uploadMessageUploadCase: MessageUploadCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            uploadMessageUploadCase()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        private const val MAX_RETRIES = 3
    }
}