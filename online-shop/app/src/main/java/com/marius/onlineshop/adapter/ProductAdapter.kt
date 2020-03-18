package com.marius.onlineshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.marius.onlineshop.R
import com.marius.onlineshop.databinding.ItemProductBinding
import com.marius.onlineshop.helper.opensDetails
import com.marius.onlineshop.model.Product
import com.marius.onlineshop.viewModel.ItemProductViewModel


class ProductAdapter(private val callback: opensDetails) : RecyclerView.Adapter<ProductAdapter.ProductVH>(),
    opensDetails {

    override fun onItemClicked(id:Int) {
        callback.onItemClicked(id)
    }

    private val products = mutableListOf<Product>()

    fun setProducts(productList: List<Product>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        return ProductVH(parent)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        holder.bind(products[position])
    }

    inner class ProductVH(
        private val parent: ViewGroup,
        private val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.viewModel = ItemProductViewModel(product,this@ProductAdapter)
        }
    }
}