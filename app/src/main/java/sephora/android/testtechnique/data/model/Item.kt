package sephora.android.testtechnique.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey val id: Int,
    val description: String,
    val location: Long,
    val image: String?
)
