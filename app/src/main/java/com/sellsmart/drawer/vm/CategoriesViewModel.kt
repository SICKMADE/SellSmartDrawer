package com.sellsmart.drawer.vm

import android.app.Application
import androidx.lifecycle.*
import com.sellsmart.drawer.data.Repository
import com.sellsmart.drawer.data.model.CategoryEntity
import kotlinx.coroutines.launch

class CategoriesViewModel(app: Application) : AndroidViewModel(app) {
  private val repo = Repository.get(app)
  private val _categories = MutableLiveData<List<CategoryEntity>>()
  val categories: LiveData<List<CategoryEntity>> = _categories
  fun load() = viewModelScope.launch { _categories.postValue(repo.getCategories()) }
  fun add(name: String) = viewModelScope.launch { repo.addCategory(name); load() }
}
