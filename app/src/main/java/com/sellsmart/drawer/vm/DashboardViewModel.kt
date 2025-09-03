package com.sellsmart.drawer.vm

import android.app.Application
import androidx.lifecycle.*
import com.sellsmart.drawer.data.DashboardMetrics
import com.sellsmart.drawer.data.Repository
import kotlinx.coroutines.launch

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
  private val repo = Repository.get(app)
  private val _metrics = MutableLiveData<DashboardMetrics>()
  val metrics: LiveData<DashboardMetrics> = _metrics
  fun load() = viewModelScope.launch { _metrics.postValue(repo.metrics()) }
}
