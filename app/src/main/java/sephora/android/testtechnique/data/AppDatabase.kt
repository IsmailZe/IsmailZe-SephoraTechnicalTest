package sephora.android.testtechnique.data

import androidx.room.Database
import androidx.room.RoomDatabase
import sephora.android.testtechnique.data.dao.ItemsDao
import sephora.android.testtechnique.data.model.Item

@Database(
    entities = [Item::class],
    version = 11,
    exportSchema = false,
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun ItemsDao(): ItemsDao
}

