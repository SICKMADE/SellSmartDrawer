package com.sellsmart.drawer.ui.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sellsmart.drawer.data.model.CategoryEntity
import com.sellsmart.drawer.databinding.FragmentAddItemBinding
import com.sellsmart.drawer.vm.CategoriesViewModel
import com.sellsmart.drawer.vm.ItemsViewModel

class AddItemFragment : Fragment() {
  private var _binding: FragmentAddItemBinding? = null
  private val binding get() = _binding!!
  private val itemsVM: ItemsViewModel by viewModels()
  private val categoriesVM: CategoriesViewModel by viewModels()

  private var picked: Uri? = null
  private var categories: List<CategoryEntity> = emptyList()

  private val picker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
    if (r.resultCode == Activity.RESULT_OK) {
      picked = r.data?.data
      binding.ivPicked.setImageURI(picked)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentAddItemBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    categoriesVM.categories.observe(viewLifecycleOwner) {
      categories = it
      val names = it.map { c -> c.name }
      binding.spCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)
    }
    categoriesVM.load()

    binding.btnPickImage.setOnClickListener {
      val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "image/*"
      }
      picker.launch(intent)
    }

    binding.btnSave.setOnClickListener {
      val title = binding.etTitle.text?.toString()?.trim().orEmpty()
      val desc = binding.etDescription.text?.toString()?.trim().orEmpty()
      val price = binding.etPrice.text?.toString()?.toDoubleOrNull() ?: 0.0
      if (title.isEmpty() || categories.isEmpty()) {
        Toast.makeText(requireContext(), "Title and Category required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      val pos = binding.spCategory.selectedItemPosition
      val cat = categories[pos]
      itemsVM.add(title, desc, price, cat.id, cat.name, picked)
      Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
      binding.etTitle.setText(""); binding.etDescription.setText(""); binding.etPrice.setText("")
      picked = null; binding.ivPicked.setImageDrawable(null)
    }
  }

  override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
