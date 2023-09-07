package com.smartmir.shoppinglist.presenter.shop_list_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.smartmir.shoppinglist.data.ShopListRepositoryImpl
import com.smartmir.shoppinglist.domain.*

class MainViewModel : ViewModel() {

    private val shopListRepositoryImpl = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(shopListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepositoryImpl)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopListRepositoryImpl)

    val shopList : LiveData<List<ShopItem>> = getShopListUseCase.getShopList()

    fun changeEnabledShopItem(oldShopItem: ShopItem) {
        val newShopItem = oldShopItem.copy(enabled = !oldShopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItemUseCase(shopItem)
    }
}