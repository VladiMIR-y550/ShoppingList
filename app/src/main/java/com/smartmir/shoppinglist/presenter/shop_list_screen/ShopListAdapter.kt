package com.smartmir.shoppinglist.presenter.shop_list_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.smartmir.shoppinglist.databinding.ItemShopDisabledBinding
import com.smartmir.shoppinglist.databinding.ItemShopEnabledBinding
import com.smartmir.shoppinglist.domain.ShopItem
import com.smartmir.shoppinglist.presenter.base.BaseViewHolder

class ShopListAdapter(
    private val onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null,
    private val onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null,
) : ListAdapter<ShopItem, BaseViewHolder>(ShopItemDiffCallback()) {

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
        val currentShopItem = getItem(position)
        holder.onBind(currentShopItem)
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(currentShopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(currentShopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLE
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLE = 101
    }
}