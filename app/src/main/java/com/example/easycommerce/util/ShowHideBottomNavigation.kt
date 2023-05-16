package com.example.easycommerce.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.easycommerce.R
import com.example.easycommerce.view.screen.shopping.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
    bottomNavigationView.visibility = View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
    bottomNavigationView.visibility = View.VISIBLE
}