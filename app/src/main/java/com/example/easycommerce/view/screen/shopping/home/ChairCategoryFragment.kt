package com.example.easycommerce.view.screen.shopping.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.easycommerce.R
import com.example.easycommerce.databinding.FragmentChairCategoryBinding

class ChairCategoryFragment : Fragment() {

    private lateinit var binding: FragmentChairCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChairCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}