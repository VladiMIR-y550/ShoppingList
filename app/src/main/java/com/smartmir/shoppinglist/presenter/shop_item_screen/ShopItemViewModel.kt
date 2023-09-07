package com.smartmir.shoppinglist.presenter.shop_item_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartmir.shoppinglist.data.ShopListRepositoryImpl
import com.smartmir.shoppinglist.domain.AddShopItemUseCase
import com.smartmir.shoppinglist.domain.EditShopItemUseCase
import com.smartmir.shoppinglist.domain.GetShopItemUseCase
import com.smartmir.shoppinglist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val shopListRepositoryImpl = ShopListRepositoryImpl

    private val addShopItemUseCase = AddShopItemUseCase(shopListRepositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(shopListRepositoryImpl)
    private val getShopItemUseCase = GetShopItemUseCase(shopListRepositoryImpl)

    private val initialCurrentShopItem = MutableLiveData<ShopItem>()
    val currentShopItem: LiveData<ShopItem> get() = initialCurrentShopItem
    private val initialShouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen:LiveData<Unit> get() = initialShouldCloseScreen
    private val initialErrorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean> get() = initialErrorInputName
    private val initialErrorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean> get() = initialErrorInputCount


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isFieldsValid = validateInput(name, count)
        if (isFieldsValid) {
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isFieldsValid = validateInput(name, count)
        if (isFieldsValid) {
            val shopItem = currentShopItem.value
            shopItem?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
            }
            finishWork()
        }
    }

    fun getShopItemId(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItemById(shopItemId)
        initialCurrentShopItem.value = item
    }

    fun resetErrorInputName() {
        initialErrorInputName.value = false
    }

    fun resetErrorInputCount() {
        initialErrorInputCount.value = false
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            initialErrorInputName.value = true
            result = false
        } else {
            initialErrorInputName.value = false
        }

        if (count <= 0) {
            result = false
            initialErrorInputCount.value = true
        } else {
            initialErrorInputCount.value = false
        }
        return result
    }

    private fun finishWork() {
        initialShouldCloseScreen.value = Unit
    }
}