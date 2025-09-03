package com.sellsmart.drawer.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sellsmart.drawer.R

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
    val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.menu_open, R.string.menu_close)
    drawer.addDrawerListener(toggle)
    toggle.syncState()

    val navController = findNavController(R.id.nav_host_fragment)
    val navView = findViewById<NavigationView>(R.id.navigationView)
    navView.setupWithNavController(navController)
  }
}
