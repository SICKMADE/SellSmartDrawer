package com.sellsmart.drawer.vm

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import com.sellsmart.drawer.data.Repository
import com.sellsmart.drawer.data.model.ItemEntity
import kotlinx.coroutines.launch

class ItemsViewModel(app: Application) : AndroidViewModel(app) {
  private val repo = Repository.get(app)
  private val _items = MutableLiveData<List<ItemEntity>>()
  val items: LiveData<List<ItemEntity>> = _items
  fun load() = viewModelScope.launch { _items.postValue(repo.getItems()) }
  fun markSold(id: Long, price: Double) = viewModelScope.launch { repo.markItemSold(id, price); load() }
  fun add(title: String, desc: String, price: Double, categoryId: Long, categoryName: String, uri: Uri?) =
    viewModelScope.launch { repo.addItem(title, desc, price, categoryId, categoryName, uri); load() }
}
