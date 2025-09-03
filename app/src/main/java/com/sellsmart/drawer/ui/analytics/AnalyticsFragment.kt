package com.sellsmart.drawer.ui.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.*
import com.sellsmart.drawer.databinding.FragmentAnalyticsBinding
import com.sellsmart.drawer.vm.AnalyticsViewModel

class AnalyticsFragment : Fragment() {
  private var _binding: FragmentAnalyticsBinding? = null
  private val binding get() = _binding!!
  private val vm: AnalyticsViewModel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    vm.pie.observe(viewLifecycleOwner) { map ->
      val entries = map.entries.map { PieEntry(it.value.toFloat(), it.key) }
      val ds = PieDataSet(entries, "Status")
      binding.pieChart.data = PieData(ds)
      binding.pieChart.invalidate()
    }
    vm.bar.observe(viewLifecycleOwner) { map ->
      val keys = map.keys.toList()
      val entries = map.entries.mapIndexed { i, e -> BarEntry(i.toFloat(), e.value.toFloat()) }
      val ds = BarDataSet(entries, "Revenue by Category")
      binding.barChart.data = BarData(ds)
      binding.barChart.xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(keys)
      binding.barChart.xAxis.granularity = 1f
      binding.barChart.invalidate()
    }
    vm.load()
  }
  override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
