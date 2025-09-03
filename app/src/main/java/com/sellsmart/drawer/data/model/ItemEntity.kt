package com.sellsmart.drawer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val description: String,
  val categoryId: Long,
  val categoryName: String,
  val price: Double,
  val imageUri: String = "",
  val status: String = Status.FOR_SALE.name,
  val createdAt: Long = System.currentTimeMillis(),
  val soldPrice: Double? = null,
  val soldAt: Long? = null
)
