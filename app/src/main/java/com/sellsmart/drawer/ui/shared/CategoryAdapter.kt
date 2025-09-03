package com.sellsmart.drawer.ui.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sellsmart.drawer.data.model.CategoryEntity
import com.sellsmart.drawer.databinding.ItemCategoryBinding

class CategoryAdapter(private val onClick: (CategoryEntity) -> Unit) :
  ListAdapter<CategoryEntity, CategoryAdapter.VH>(DIFF) {

  companion object {
    val DIFF = object : DiffUtil.ItemCallback<CategoryEntity>() {
      override fun areItemsTheSame(old: CategoryEntity, new: CategoryEntity) = old.id == new.id
      override fun areContentsTheSame(old: CategoryEntity, new: CategoryEntity) = old == new
    }
  }

  inner class VH(val b: ItemCategoryBinding) : RecyclerView.ViewHolder(b.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val b = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return VH(b)
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    val item = getItem(position)
    holder.b.tvName.text = item.name
    holder.b.tvCount.text = ""
    holder.itemView.setOnClickListener { onClick(item) }
  }
}
