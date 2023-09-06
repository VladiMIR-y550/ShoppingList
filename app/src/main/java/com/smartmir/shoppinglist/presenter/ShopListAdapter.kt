package com.smartmir.shoppinglist.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartmir.shoppinglist.databinding.ItemShopDisabledBinding
import com.smartmir.shoppinglist.databinding.ItemShopEnabledBinding
import com.smartmir.shoppinglist.domain.ShopItem
import com.smartmir.shoppinglist.presenter.base.BaseViewHolder

class ShopListAdapter(
    private val onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null,
    private val onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null,
) : RecyclerView.Adapter<BaseViewHolder>() {

    var items = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(newItems = value, oldItems = items)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ENABLED -> {
                ItemEnabledVewHolder(
                    ItemShopEnabledBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_DISABLE -> {
                ItemDisabledViewHolder(
                    ItemShopDisabledBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            else -> throw RuntimeException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentShopItem = items[position]
        holder.onBind(currentShopItem)
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(currentShopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(currentShopItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLE
        }
    }

    class ItemEnabledVewHolder(private val binding: ItemShopEnabledBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(shopItem: ShopItem) {
            binding.tvNameItem.text = shopItem.name
            binding.tvCount.text = shopItem.count.toString()
        }
    }

    class ItemDisabledViewHolder(private val binding: ItemShopDisabledBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(shopItem: ShopItem) {
            binding.tvNameItem.text = shopItem.name
            binding.tvCount.text = shopItem.count.toString()
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLE = 101
    }
}