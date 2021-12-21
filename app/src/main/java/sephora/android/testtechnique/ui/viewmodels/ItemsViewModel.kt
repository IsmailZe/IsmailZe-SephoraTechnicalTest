package sephora.android.testtechnique.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import sephora.android.testtechnique.BaseApplication
import sephora.android.testtechnique.data.model.Item
import sephora.android.testtechnique.data.repository.ItemRepository
import sephora.android.testtechnique.utils.AppResult
import sephora.android.testtechnique.worker.ItemsWorker


class ItemsViewModel
@ViewModelInject constructor(
    itemRepository: ItemRepository,
    application: BaseApplication,
    @Assisted private val state: SavedStateHandle,
) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)
    val searchQuery = state.getLiveData(SEARCH_QUERY, "")

    private val itemsFlow = itemRepository.getItemsFromDB()

    internal val items: LiveData<AppResult<List<Item>>> = itemsFlow.asLiveData()

    init {
        val constraint: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest =
            PeriodicWorkRequest.Builder(
                ItemsWorker::class.java,
                WORKER_PERIODIC_DELAY,
                TimeUnit.MINUTES
            )
                .setConstraints(constraint)
                .addTag(TAG_OUTPUT)
                .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }


    companion object {
        const val TAG_OUTPUT = "Track_TAG"
        const val WORKER_NAME = "my_unique_worker"
        const val SEARCH_QUERY = "search_query"
        const val WORKER_PERIODIC_DELAY = 15L
    }
}
