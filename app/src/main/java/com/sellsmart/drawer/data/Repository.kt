package com.sellsmart.drawer.data

import android.content.Context
import android.net.Uri
import androidx.room.Room
import com.sellsmart.drawer.data.db.AppDatabase
import com.sellsmart.drawer.data.model.CategoryEntity
import com.sellsmart.drawer.data.model.ItemEntity
import com.sellsmart.drawer.data.model.Status

class Repository private constructor(ctx: Context) {
  private val db = Room.databaseBuilder(ctx, AppDatabase::class.java, "sellsmart.db").build()
  private val cats = db.categoryDao()
  private val items = db.itemDao()

  companion object {
    @Volatile private var INSTANCE: Repository? = null
    fun get(ctx: Context): Repository =
      INSTANCE ?: synchronized(this) { Repository(ctx.applicationContext).also { INSTANCE = it } }
  }

  suspend fun getCategories(): List<CategoryEntity> = cats.getAll()
  suspend fun addCategory(name: String) { cats.insert(CategoryEntity(name = name)) }

  suspend fun getItems(): List<ItemEntity> = items.getAll()

  suspend fun addItem(title: String, desc: String, price: Double, categoryId: Long, categoryName: String, imageUri: Uri?) {
    val uriStr = imageUri?.toString() ?: ""
    items.insert(ItemEntity(title = title, description = desc, price = price, categoryId = categoryId, categoryName = categoryName, imageUri = uriStr))
  }

  suspend fun markItemSold(itemId: Long, soldPrice: Double) {
    val list = items.getAll()
    val item = list.firstOrNull { it.id == itemId } ?: return
    items.update(item.copy(status = Status.SOLD.name, soldPrice = soldPrice, soldAt = System.currentTimeMillis()))
  }

  suspend fun metrics(): DashboardMetrics {
    val all = items.getAll()
    val total = all.size
    val forSale = all.count { it.status == Status.FOR_SALE.name }
    val sold = all.count { it.status == Status.SOLD.name }
    val revenue = all.mapNotNull { it.soldPrice }.sum()
    return DashboardMetrics(total, forSale, sold, revenue)
  }

  suspend fun revenueByCategory(): Map<String, Double> {
    val all = items.getAll().filter { it.status == Status.SOLD.name && it.soldPrice != null }
    return all.groupBy { it.categoryName }.mapValues { e -> e.value.sumOf { it.soldPrice ?: 0.0 } }
  }

  suspend fun countsByStatus(): Map<String, Int> {
    val all = items.getAll()
    return mapOf(
      "For Sale" to all.count { it.status == Status.FOR_SALE.name },
      "Sold" to all.count { it.status == Status.SOLD.name }
    )
  }
}

data class DashboardMetrics(val totalItems: Int, val forSale: Int, val sold: Int, val revenue: Double)
