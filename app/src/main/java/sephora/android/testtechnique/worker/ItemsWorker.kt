package sephora.android.testtechnique.worker

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import sephora.android.testtechnique.data.repository.ItemRepository
import sephora.android.testtechnique.utils.AppResult

class ItemsWorker
@WorkerInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val itemRepository: ItemRepository,
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        val followersRep = itemRepository.getItems()
        followersRep.collect { items ->
            try {
                when (items) {
                    is AppResult.Success -> {
                        withContext(Dispatchers.IO) {
                            items.successData.forEach {
                                itemRepository.getItems().collect()
                            }
                            Log.i(TAG, "doWork: ${items.successData}")
                            Result.success()
                        }
                    }
                    is AppResult.Error -> {
                        Log.i(TAG, "doWork: ${items.exception.message ?: "error"}")
                        Result.failure()
                    }
                    is AppResult.Loading -> {
                    }
                }
            } catch (ex: Exception) {
                Log.i(TAG, "doWork: ${ex.message ?: "error"}")
                Result.failure()
            }
        }
        Result.success()
    }

    companion object {
        private const val TAG = "ItemsWorker"
    }
}
