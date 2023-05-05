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
import com.example.easycommerce.model.data.Product

class BestDealsAdapter(): RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {

    inner class BestDealsViewHolder(private val binding: BestDealsRvItemBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(product: Product){
                    binding.apply {
                        Glide.with(itemView).load(product.images[0]).into(imageBestDealsRvItem)
                        product.offerPercentage?.let {
                            val remainingPricePercenteage = 1f - it
                            val priceAfterOffer = remainingPricePercenteage * product.price
                            tvBestDealsNewPrice.text = "$ ${String.format("%.2f",priceAfterOffer)}"
                            tvBestDealsOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        if (product.offerPercentage == null)
                            tvBestDealsNewPrice.visibility = View.INVISIBLE
                        tvBestDealsOldPrice.text = "$ ${product.price}"
                        tvBestDealsName.text = product.name
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
        return BestDealsViewHolder(
            BestDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}