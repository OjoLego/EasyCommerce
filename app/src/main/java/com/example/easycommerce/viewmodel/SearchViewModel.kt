package com.example.easycommerce.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easycommerce.model.data.Product
import com.example.easycommerce.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _searchResults = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val searchResults: StateFlow<Resource<List<Product>>> = _searchResults

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _searchResults.emit(Resource.Loading())
        }

        val capitalizeQuery = query.capitalize()

        firestore.collection("Products")
            .orderBy("name")
            .startAt(capitalizeQuery)
            .endAt(capitalizeQuery + "\uf8ff")
            .get()
            .addOnSuccessListener { result ->
                val searchResults = result.toObjects(Product::class.java)
                Log.d("SearchViewModel", "Search results: $searchResults")
                viewModelScope.launch {
                    _searchResults.emit(Resource.Success(searchResults))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _searchResults.emit(Resource.Error(it.message.toString()))
                }
            }


//        val lowercaseQuery = query.capitalize()
//
//        firestore.collection("Products")
//            .whereGreaterThanOrEqualTo("name", lowercaseQuery)
//            .get()
//            .addOnSuccessListener { result ->
//                val searchResults = result.toObjects(Product::class.java)
//                Log.d("SearchViewModel", "Search results: $searchResults")
//                viewModelScope.launch {
//                    _searchResults.emit(Resource.Success(searchResults))
//                }
//            }
//            .addOnFailureListener {
//                viewModelScope.launch {
//                    _searchResults.emit(Resource.Error(it.message.toString()))
//                }
//            }
    }

}