package com.idreesrazak.mobileassignment.data

import androidx.room.*

@Dao
interface ItemDao {

    @Query("SELECT * FROM ITEM_DATA ORDER BY itemId ASC")
    fun readAllData(): List<Item>

    @Insert
    fun insertItem(item: Item) : Long

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Query("DELETE FROM ITEM_DATA")
    fun deleteAllItems()
}