package sephora.android.testtechnique.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sephora.android.testtechnique.data.model.Item

@Dao
interface ItemsDao {

    @Query("SELECT * FROM item_table")
    fun getItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items: Item)
}
