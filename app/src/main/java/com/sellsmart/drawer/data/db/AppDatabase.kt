package com.sellsmart.drawer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sellsmart.drawer.data.model.CategoryEntity
import com.sellsmart.drawer.data.model.ItemEntity

@Database(entities = [CategoryEntity::class, ItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun categoryDao(): CategoryDao
  abstract fun itemDao(): ItemDao
}
