package com.smartmir.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItemUseCase(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItemById(shopItemId: Int): ShopItem

    fun getShopListLiveData(): LiveData<List<ShopItem>>
}