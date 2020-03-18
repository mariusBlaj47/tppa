package com.marius.onlineshop.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marius.onlineshop.helper.opensDetails
import com.marius.onlineshop.model.Product

class ItemProductViewModel(product: Product, private val callback: opensDetails) : ViewModel() {
    val product = MutableLiveData<Product>(product)

    fun onClick() {
        product.value?.id?.let { id ->
            callback.onItemClicked(id)
        }
    }
}