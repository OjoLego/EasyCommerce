package com.example.easycommerce.view.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easycommerce.R
import com.example.easycommerce.databinding.OrderItemBinding
import com.example.easycommerce.model.data.order.Order
import com.example.easycommerce.model.data.order.OrderStatus
import com.example.easycommerce.model.data.order.getOrderStatus

class AllOrdersAdapter: RecyclerView.Adapter<AllOrdersAdapter.AllOrdersViewHolder>(){
   inner class AllOrdersViewHolder(private val binding: OrderItemBinding): ViewHolder(binding.root) {
       fun bind(order: Order){
           binding.apply {
               tvOrderId.text = order.orderId.toString()
               tvOrderDate.text = order.date
               val resources = itemView.resources

               val colorDrawable = when(getOrderStatus(order.orderStatus)){
                   is OrderStatus.Ordered -> {
                       ColorDrawable(resources.getColor(R.color.g_orange_yellow))
                   }
                   is OrderStatus.Confirmed -> {
                       ColorDrawable(resources.getColor(R.color.g_green))
                   }
                   is OrderStatus.Delivered -> {
                       ColorDrawable(resources.getColor(R.color.g_green))
                   }
                   is OrderStatus.Shipped -> {
                       ColorDrawable(resources.getColor(R.color.g_green))
                   }
                   is OrderStatus.Canceled -> {
                       ColorDrawable(resources.getColor(R.color.g_red))
                   }
                   is OrderStatus.Returned -> {
                       ColorDrawable(resources.getColor(R.color.g_red))
                   }
               }

               imageOrderState.setImageDrawable(colorDrawable)
           }
       }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.products == newItem.products
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrdersViewHolder {
        return AllOrdersViewHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllOrdersViewHolder, position: Int) {
        val order = differ.currentList[position]
        holder.bind(order)

        holder.itemView.setOnClickListener {
            onClick?.invoke(order)
        }
    }

    var onClick: ((Order) -> Unit)? = null


}