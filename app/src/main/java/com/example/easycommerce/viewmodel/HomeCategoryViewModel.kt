package com.example.easycommerce.viewmodel

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
class HomeCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    private val _bestDealsProduct = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDealsProduct: StateFlow<Resource<List<Product>>> = _bestDealsProduct

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts: StateFlow<Resource<List<Product>>> = _bestProducts

    private val pagingInfo = PagingInfo()

    init {
        fetchSpecialProducts()
        fetchBestDeals()
        fetchBestProducts()
    }

    fun fetchSpecialProducts(){

        viewModelScope.launch { _specialProducts.emit(Resource.Loading()) }

        firestore.collection("Products")
            .whereEqualTo("category","Special Products").get()
            .addOnSuccessListener { result ->
                val specialProductsList = result.toObjects(Product::class.java)
                viewModelScope.launch { _specialProducts.emit(Resource.Success(specialProductsList))}
            }
            .addOnFailureListener {
                viewModelScope.launch { _specialProducts.emit(Resource.Error(it.message.toString())) }
            }
    }

    fun fetchBestDeals(){

        viewModelScope.launch { _bestDealsProduct.emit(Resource.Loading()) }

        firestore.collection("Products")
            .whereEqualTo("category","Best Deals").get()
            .addOnSuccessListener { result ->
                val bestDealsProducts = result.toObjects(Product::class.java)
                viewModelScope.launch { _bestDealsProduct.emit(Resource.Success(bestDealsProducts))}
            }
            .addOnFailureListener {
                viewModelScope.launch { _bestDealsProduct.emit(Resource.Error(it.message.toString())) }
            }
    }

    fun fetchBestProducts() {

        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch { _bestProducts.emit(Resource.Loading()) }

            firestore.collection("Products").limit(pagingInfo.bestProductsPage * 10).get()
                .addOnSuccessListener { result ->
                    val bestProducts = result.toObjects(Product::class.java)
                    pagingInfo.isPagingEnd = bestProducts == pagingInfo.oldBestProduct
                    pagingInfo.oldBestProduct = bestProducts
                    viewModelScope.launch { _bestProducts.emit(Resource.Success(bestProducts)) }
                }
                .addOnFailureListener {
                    viewModelScope.launch { _bestProducts.emit(Resource.Error(it.message.toString())) }
                }
            pagingInfo.bestProductsPage++
        }
    }
        internal data class PagingInfo(
            var bestProductsPage: Long = 1,
            var oldBestProduct: List<Product> = emptyList(),
            var isPagingEnd: Boolean = false
        )

}