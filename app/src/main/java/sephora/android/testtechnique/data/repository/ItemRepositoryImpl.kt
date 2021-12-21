package sephora.android.testtechnique.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import sephora.android.testtechnique.R
import sephora.android.testtechnique.api.Api
import sephora.android.testtechnique.data.dao.ItemsDao
import sephora.android.testtechnique.data.model.Item
import sephora.android.testtechnique.utils.AppResult
import sephora.android.testtechnique.utils.NetworkManager
import sephora.android.testtechnique.utils.Utils

class ItemRepositoryImpl @Inject constructor(
    private val api: Api,
    @ApplicationContext private val context: Context,
    private val itemsDao: ItemsDao,
) : ItemRepository {
    override fun getItems(): Flow<AppResult<List<Item>>> = flow {
        emit(AppResult.Loading)
        if (NetworkManager.isOnline(context)) {
            try {
                val response = api.getItems()
                if (response.isSuccessful) {
                    response.body()?.let { items ->
                        for (track in items) {
                            itemsDao.addItems(track)
                        }
                        emit(AppResult.Success(items))
                    }
                } else {
                    emit(Utils.handleApiError(response))
                }
            } catch (e: Exception) {
                emit(AppResult.Error(e))
            }
        } else {
            emit(context.noNetworkConnectivityError())
        }
    }

    override fun getItemsFromDB(
    ): Flow<AppResult<List<Item>>> = flow {
        if (!NetworkManager.isOnline(context)) {
            emit(context.noNetworkConnectivityError())
        }
        emit(AppResult.Loading)
        itemsDao.getItems().collect {
            emit(AppResult.Success(it))
        }
    }

    fun Context.noNetworkConnectivityError(): AppResult.Error =
        AppResult.Error(Exception(this.resources.getString(R.string.no_network_connectivity)))
}
