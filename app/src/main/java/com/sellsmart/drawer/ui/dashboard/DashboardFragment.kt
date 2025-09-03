package com.sellsmart.drawer.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sellsmart.drawer.databinding.FragmentDashboardBinding
import com.sellsmart.drawer.ui.shared.CategoryAdapter
import com.sellsmart.drawer.vm.CategoriesViewModel
import com.sellsmart.drawer.vm.DashboardViewModel

class DashboardFragment : Fragment() {
  private var _binding: FragmentDashboardBinding? = null
  private val binding get() = _binding!!
  private val dashVM: DashboardViewModel by viewModels()
  private val catVM: CategoriesViewModel by viewModels()
  private val adapter = CategoryAdapter {}

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
    binding.rvCategories.adapter = adapter

    dashVM.metrics.observe(viewLifecycleOwner) {
      binding.tvTotalItems.text = it.totalItems.toString()
      binding.tvForSale.text = it.forSale.toString()
      binding.tvSold.text = it.sold.toString()
      binding.tvRevenue.text = "$" + String.format("%.2f", it.revenue)
    }
    catVM.categories.observe(viewLifecycleOwner) { adapter.submitList(it) }

    dashVM.load()
    catVM.load()
  }

  override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
