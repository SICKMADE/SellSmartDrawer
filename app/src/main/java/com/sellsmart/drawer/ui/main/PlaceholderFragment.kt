package com.sellsmart.drawer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sellsmart.drawer.R

class PlaceholderFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_placeholder, container, false)
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val tv = view.findViewById<TextView>(R.id.tv)
    tv.text = activity?.title ?: "Screen"
  }
}
