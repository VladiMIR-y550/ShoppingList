package com.smartmir.shoppinglist.presenter.shop_list_screen

import com.smartmir.shoppinglist.databinding.ItemShopEnabledBinding
import com.smartmir.shoppinglist.domain.ShopItem
import com.smartmir.shoppinglist.presenter.base.BaseViewHolder

class ItemEnabledVewHolder(private val binding: ItemShopEnabledBinding) :
    BaseViewHolder(binding.root) {

    override fun onBind(shopItem: ShopItem) {
        binding.tvNameItem.text = shopItem.name
        binding.tvCount.text = shopItem.count.toString()
    }
}