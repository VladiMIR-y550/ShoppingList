package com.smartmir.shoppinglist.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartmir.shoppinglist.data.ShopListRepositoryImpl
import com.smartmir.shoppinglist.domain.*

class MainViewModel: ViewModel() {

    private val shopListRepositoryImpl = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(shopListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepositoryImpl)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopListRepositoryImpl)

    private val initialShopList = MutableLiveData<List<ShopItem>>()
    val shopList : LiveData<List<ShopItem>> = initialShopList

    init {
        getShopList()
    }

    fun changeEnabledShopItem(oldShopItem: ShopItem) {
        val newShopItem = oldShopItem.copy(enabled = !oldShopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
        getShopList()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItemUseCase(shopItem)
        getShopList()
    }

    private fun getShopList() {
        initialShopList.value = getShopListUseCase.getShopList()
    }
}