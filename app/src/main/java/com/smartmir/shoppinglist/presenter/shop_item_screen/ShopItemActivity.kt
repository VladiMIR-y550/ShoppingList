package com.smartmir.shoppinglist.presenter.shop_item_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.smartmir.shoppinglist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {

    private var initialBinding: ActivityShopItemBinding? = null
    private val binding get() = initialBinding!!

    private lateinit var viewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialBinding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
    }

    override fun onDestroy() {
        initialBinding = null
        super.onDestroy()
    }
}