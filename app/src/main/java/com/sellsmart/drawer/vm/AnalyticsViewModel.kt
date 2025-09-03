package com.sellsmart.drawer.vm

import android.app.Application
import androidx.lifecycle.*
import com.sellsmart.drawer.data.Repository
import kotlinx.coroutines.launch

class AnalyticsViewModel(app: Application) : AndroidViewModel(app) {
  private val repo = Repository.get(app)
  private val _pie = MutableLiveData<Map<String, Int>>()
  val pie: LiveData<Map<String, Int>> = _pie
  private val _bar = MutableLiveData<Map<String, Double>>()
  val bar: LiveData<Map<String, Double>> = _bar
  fun load() = viewModelScope.launch { _pie.postValue(repo.countsByStatus()); _bar.postValue(repo.revenueByCategory()) }
}
