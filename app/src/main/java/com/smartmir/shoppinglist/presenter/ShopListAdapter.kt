package com.smartmir.shoppinglist.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smartmir.shoppinglist.R
import com.smartmir.shoppinglist.databinding.ItemShopDisabledBinding
import com.smartmir.shoppinglist.databinding.ItemShopEnabledBinding
import com.smartmir.shoppinglist.domain.ShopItem
import com.smartmir.shoppinglist.presenter.base.BaseViewHolder

class ShopListAdapter(
    private val onLongClick: (shopItem: ShopItem) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    var items = listOf<ShopItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == R.layout.item_shop_enabled) {
            val binding =
                ItemShopEnabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemEnabledVewHolder(binding)
        } else {
            val binding =
                ItemShopDisabledBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemDisabledViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentShopItem = items[position]
        holder.onBind(currentShopItem)
        holder.itemView.setOnLongClickListener {
            onLongClick(currentShopItem)
            true
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].enabled) {
            R.layout.item_shop_enabled
        } else {
            R.layout.item_shop_disabled
        }
    }

    class ItemEnabledVewHolder(private val binding: ItemShopEnabledBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(shopItem: ShopItem) {
            binding.tvNameItem.text = shopItem.name
            binding.tvCount.text = shopItem.count.toString()
            binding.tvNameItem.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.purple_700
                )
            )
        }
    }

    class ItemDisabledViewHolder(private val binding: ItemShopDisabledBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(shopItem: ShopItem) {
            binding.tvNameItem.text = shopItem.name
            binding.tvCount.text = shopItem.count.toString()
        }
    }
}