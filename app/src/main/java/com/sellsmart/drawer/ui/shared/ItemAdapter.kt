package com.sellsmart.drawer.ui.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sellsmart.drawer.data.model.ItemEntity
import com.sellsmart.drawer.databinding.ItemItemBinding

class ItemAdapter(private val onMarkSold: (Long) -> Unit) :
  ListAdapter<ItemEntity, ItemAdapter.VH>(DIFF) {

  companion object {
    val DIFF = object : DiffUtil.ItemCallback<ItemEntity>() {
      override fun areItemsTheSame(old: ItemEntity, new: ItemEntity) = old.id == new.id
      override fun areContentsTheSame(old: ItemEntity, new: ItemEntity) = old == new
    }
  }

  inner class VH(val b: ItemItemBinding) : RecyclerView.ViewHolder(b.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    val b = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return VH(b)
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    val item = getItem(position)
    holder.b.tvTitle.text = item.title
    holder.b.tvCategory.text = item.categoryName
    holder.b.tvPrice.text = "$" + String.format("%.2f", item.price)
    if (item.imageUri.isNotEmpty()) holder.b.ivThumb.load(item.imageUri)
    holder.b.btnMarkSold.setOnClickListener { onMarkSold(item.id) }
  }
}
