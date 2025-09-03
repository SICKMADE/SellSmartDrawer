package com.sellsmart.drawer.ui.categories

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sellsmart.drawer.databinding.FragmentCategoriesBinding
import com.sellsmart.drawer.ui.shared.CategoryAdapter
import com.sellsmart.drawer.vm.CategoriesViewModel

class CategoriesFragment : Fragment() {
  private var _binding: FragmentCategoriesBinding? = null
  private val binding get() = _binding!!
  private val vm: CategoriesViewModel by viewModels()
  private val adapter = CategoryAdapter {}

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
    binding.rvCategories.adapter = adapter
    vm.categories.observe(viewLifecycleOwner) { adapter.submitList(it) }
    vm.load()

    binding.btnAddCategory.setOnClickListener {
      val input = EditText(requireContext())
      AlertDialog.Builder(requireContext())
        .setTitle("Add Category")
        .setView(input)
        .setPositiveButton("Save") { _, _ ->
          val name = input.text.toString().trim()
          if (name.isNotEmpty()) vm.add(name)
        }.setNegativeButton("Cancel", null).show()
    }
  }
  override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
