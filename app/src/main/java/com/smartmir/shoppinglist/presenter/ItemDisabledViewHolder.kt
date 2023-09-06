package com.smartmir.shoppinglist.presenter

import com.smartmir.shoppinglist.databinding.ItemShopDisabledBinding
import com.smartmir.shoppinglist.domain.ShopItem
import com.smartmir.shoppinglist.presenter.base.BaseViewHolder

class ItemDisabledViewHolder(private val binding: ItemShopDisabledBinding) :
    BaseViewHolder(binding.root) {

    override fun onBind(shopItem: ShopItem) {
        binding.tvNameItem.text = shopItem.name
        binding.tvCount.text = shopItem.count.toString()
    }
}