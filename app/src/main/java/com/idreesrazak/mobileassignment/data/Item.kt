package com.idreesrazak.mobileassignment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item_data")
data class Item(
    @PrimaryKey(autoGenerate = true) var itemId: Long?,
    @ColumnInfo(name = "itemName") var name: String,
    @ColumnInfo(name = "category") var category: Int,
    @ColumnInfo(name = "estimatedPrice") var price: Int,
    @ColumnInfo(name = "status") var status: Boolean,
    @ColumnInfo(name = "description") var description: String
) :Serializable
