package com.mudassir.pizzadeliveryapp.ui.adapter

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
import com.mudassir.pizzadeliveryapp.databinding.SingleItemPizzaBinding

class MenuListAdapter : ListAdapter<Pizza, MenuListAdapter.ItemViewHolder>(
    Comparator()
) {

    private var callbacks: Callbacks? = null
    fun setupListener(listener: Callbacks?) {
        this.callbacks = listener
    }

    interface Callbacks {
        fun onPizzaItemClick(view: View, item: Pizza)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Log.d("TAG", "onCreateViewHolder: $viewType")
        val viewBinding =
            SingleItemPizzaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(
            viewBinding
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: SingleItemPizzaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.ivPizza
        fun bind(item: Pizza) = with(itemView) {

           val image = resources.getIdentifier(item.imageName, "drawable", context.packageName)

            image.let {
                Glide.with(this).load(it?: R.drawable.pizza_1_firmennaya)
                    .error(R.drawable.pizza_1_firmennaya).into(imageView)
            }
            binding.tvPizzaName.text = item.name
            binding.tvPizzaPrice.text = "$${item.price}"
//            binding.tvPlaceDescription.text = item.description
            binding.lyPizza.setOnClickListener {
                callbacks?.onPizzaItemClick(it, item)
            }
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