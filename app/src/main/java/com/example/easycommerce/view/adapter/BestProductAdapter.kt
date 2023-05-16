package com.example.easycommerce.view.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easycommerce.databinding.BestDealsRvItemBinding
import com.example.easycommerce.databinding.ProductRvItemBinding
import com.example.easycommerce.model.data.Product

class BestProductAdapter(): RecyclerView.Adapter<BestProductAdapter.BestProductViewHolder>() {

    inner class BestProductViewHolder(private val binding: ProductRvItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product){
            binding.apply {
                Glide.with(itemView).load(product.images[0]).into(imageBestProductRvItem)
                product.offerPercentage?.let {
                    val remainingPricePercenteage = 1f - it
                    val priceAfterOffer = remainingPricePercenteage * product.price
                    tvBestProductNewPrice.text = "$ ${String.format("%.2f",priceAfterOffer)}"
                    tvBestProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                if (product.offerPercentage == null)
                    tvBestProductNewPrice.visibility = View.INVISIBLE
                tvBestProductOldPrice.text = "$ ${product.price}"
                tvBestProductName.text = product.name
            }
        }
    }

    private val diffCallback = object: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductViewHolder {
        return BestProductViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Product) -> Unit) ?= null

}