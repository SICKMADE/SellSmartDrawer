package com.sellsmart.drawer.data.db

import androidx.room.*
import com.sellsmart.drawer.data.model.CategoryEntity
import com.sellsmart.drawer.data.model.ItemEntity

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories ORDER BY createdAt ASC")
  suspend fun getAll(): List<CategoryEntity>
  @Insert suspend fun insert(category: CategoryEntity): Long
}

@Dao
interface ItemDao {
  @Query("SELECT * FROM items ORDER BY createdAt DESC")
  suspend fun getAll(): List<ItemEntity>
  @Insert suspend fun insert(item: ItemEntity): Long
  @Update suspend fun update(item: ItemEntity)
}
