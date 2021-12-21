package sephora.android.testtechnique.data.repository

import kotlinx.coroutines.flow.Flow
import sephora.android.testtechnique.data.model.Item
import sephora.android.testtechnique.utils.AppResult

interface ItemRepository {
    fun getItems(): Flow<AppResult<List<Item>>>
    fun getItemsFromDB(): Flow<AppResult<List<Item>>>
}
