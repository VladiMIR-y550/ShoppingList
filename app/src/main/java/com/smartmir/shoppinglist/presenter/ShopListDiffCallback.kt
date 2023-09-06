package com.smartmir.shoppinglist.presenter

import androidx.recyclerview.widget.DiffUtil
import com.smartmir.shoppinglist.domain.ShopItem

class ShopListDiffCallback(
    private val newItems: List<ShopItem>,
    private val oldItems: List<ShopItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}