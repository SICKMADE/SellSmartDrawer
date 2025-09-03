package com.sellsmart.drawer.ui.items

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sellsmart.drawer.databinding.FragmentItemsBinding
import com.sellsmart.drawer.ui.shared.ItemAdapter
import com.sellsmart.drawer.vm.ItemsViewModel

class ItemsFragment : Fragment() {
  private var _binding: FragmentItemsBinding? = null
  private val binding get() = _binding!!
  private val vm: ItemsViewModel by viewModels()
  private val adapter = ItemAdapter(onMarkSold = { id ->
    val input = EditText(requireContext()); input.hint = "Sold price"
    AlertDialog.Builder(requireContext())
      .setTitle("Mark Sold")
      .setView(input)
      .setPositiveButton("OK") { _, _ ->
        val p = input.text.toString().toDoubleOrNull() ?: return@setPositiveButton
        vm.markSold(id, p)
      }.setNegativeButton("Cancel", null).show()
  })

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentItemsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
    binding.rvItems.adapter = adapter
    vm.items.observe(viewLifecycleOwner) { adapter.submitList(it) }
    vm.load()
  }

  override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
