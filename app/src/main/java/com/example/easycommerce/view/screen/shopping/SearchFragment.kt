package com.example.easycommerce.view.screen.shopping

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easycommerce.R
import com.example.easycommerce.databinding.FragmentSearchBinding
import com.example.easycommerce.util.Resource
import com.example.easycommerce.view.adapter.BestProductAdapter
import com.example.easycommerce.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var bestProductAdapter: BestProductAdapter
    val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBestProductsRv()
        onSearchArrowClick()
        searchProducts()
        setupSearchFunctionality()

        bestProductAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_searchFragment_to_productDetailsFragment,b)
        }

    }



    private fun setUpBestProductsRv() {
        bestProductAdapter = BestProductAdapter()
        binding.rvBestProduct.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            adapter = bestProductAdapter
        }
    }



    private fun onSearchArrowClick() {
        binding.searchIcon.setOnClickListener { setupSearchFunctionality() }
    }

    private fun searchProducts(){
        lifecycleScope.launchWhenStarted {
            viewModel.searchResults.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        binding.ProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        bestProductAdapter.differ.submitList(it.data)
                        binding.ProgressBar.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.ProgressBar.visibility = View.GONE
                        Log.e("Search Error",it.message.toString())
                        Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupSearchFunctionality() {
        binding.etSearchBox.addTextChangedListener { text ->
            val query = text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchProducts(query)
            }
        }

}
}