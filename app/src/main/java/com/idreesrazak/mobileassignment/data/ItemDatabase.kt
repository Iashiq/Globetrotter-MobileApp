package com.idreesrazak.mobileassignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase()
{
    abstract fun itemDao(): ItemDao
    companion object
    {
        //@Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase
        {
            if (INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ItemDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
        fun destroyInstance()
        {
            INSTANCE = null
        }
    }
}