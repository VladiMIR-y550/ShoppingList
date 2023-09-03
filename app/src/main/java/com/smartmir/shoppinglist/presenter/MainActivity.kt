package com.smartmir.shoppinglist.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.smartmir.shoppinglist.databinding.ActivityMainBinding
import com.smartmir.shoppinglist.databinding.ItemShopDisabledBinding
import com.smartmir.shoppinglist.databinding.ItemShopEnabledBinding
import com.smartmir.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private var initialBinding: ActivityMainBinding? = null
    private val binding get() = initialBinding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { items ->
            showList(items)
        }
    }

    private fun showList(shopList: List<ShopItem>) {
        binding.llShopList.removeAllViews()
        shopList.map { item ->
            val view = getShopItemView(item)
            binding.llShopList.addView(view)
        }
    }

    private fun getShopItemView(item: ShopItem): View {
        return if (item.enabled) {
            val itemView =
                ItemShopEnabledBinding.inflate(layoutInflater, binding.llShopList, false)
            itemView.tvNameItem.text = item.name
            itemView.tvCount.text = item.count.toString()
            itemView.root
        } else {
            val itemView =
                ItemShopDisabledBinding.inflate(layoutInflater, binding.llShopList, false)
            itemView.tvNameItem.text = item.name
            itemView.tvCount.text = item.count.toString()
            itemView.root
        }
    }
}