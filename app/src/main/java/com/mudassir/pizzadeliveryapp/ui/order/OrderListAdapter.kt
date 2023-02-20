package com.mudassir.pizzadeliveryapp.ui.order

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mudassir.domain.model.Pizza
import com.mudassir.pizzadeliveryapp.R
import com.mudassir.pizzadeliveryapp.databinding.SingleItemOrderBinding
import com.mudassir.pizzadeliveryapp.ui.home.HomeViewModel
import kotlin.math.log

class OrderListAdapter constructor(val viewModel: HomeViewModel) : ListAdapter<Pizza, OrderListAdapter.ItemViewHolder>(
    Comparator()
) {

    private var callbacks: CartItemListener? = null
    fun setupListener(listener: CartItemListener?) {
        this.callbacks = listener
    }

    interface CartItemListener {
//        fun onPizzaItemClick(view: View, item: Pizza)
        fun onItemIncrement(cartItem: Int)
        fun onItemDecrement(cartItem: Pizza)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Log.d("TAG", "onCreateViewHolder: $viewType")
        val viewBinding =
            SingleItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(
            viewBinding
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(getItem(position))

        holder.binding.ivAdd.setOnClickListener {
          cartItem.quantity++
            viewModel.updateCartItem(cartItem)
            notifyItemChanged(position) // Notify adapter of data change
        }

        holder.binding.ivMinus.setOnClickListener {
            if (cartItem.quantity > 0) {
                cartItem.quantity--
                viewModel.updateCartItem(cartItem)
                notifyItemChanged(position) // Notify adapter of data change
            }
        }
    }

    inner class ItemViewHolder( val binding: SingleItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.ivPizza
        fun bind(item: Pizza) = with(itemView) {

            val image = resources.getIdentifier(item.imageName, "drawable", context.packageName)

            image.let {
                Glide.with(this).load(it ?: R.drawable.pizza_1_firmennaya)
                    .error(R.drawable.pizza_1_firmennaya).into(imageView)
            }

            Log.d("TAGAdapter", "bind: ${item.name}  ${item.quantity}")
            binding.tvPizzaName.text = item.name
            binding.tvPrice.text = "$${item.price}"
            binding.tvQuantity.text = "${item.quantity}"
//            binding.ivAdd.setOnClickListener {
//                var count = item.quantity +1
////               val updatedItem = item.copy(
////                    quantity = count++
////                )
//
//                callbacks?.onItemIncrement(count)
//            }
//            binding.ivMinus.setOnClickListener {
//                val count = item.quantity - 1
//                item.copy(quantity = count)
//                callbacks?.onItemDecrement(item)
//            }
//            binding.tvPlaceDescription.text = item.description
//            binding.lyPizza.setOnClickListener {
//                callbacks?.onPizzaItemClick(it, item)
//            }
        }
    }
}

class Comparator : DiffUtil.ItemCallback<Pizza>() {

    override fun areItemsTheSame(
        oldItem: Pizza,
        newItem: Pizza
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Pizza,
        newItem: Pizza
    ): Boolean {
        return oldItem == newItem
    }

}