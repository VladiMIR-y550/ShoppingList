package com.smartmir.shoppinglist.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.smartmir.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var initialBinding: ActivityMainBinding? = null
    private val binding get() = initialBinding!!
    private lateinit var viewModel: MainViewModel
    private val adapter = ShopListAdapter {
        viewModel.changeEnabledShopItem(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { items ->
            binding.rvShopList.post {
                adapter.items = items
            }
        }
    }

    private fun setupView() {
        binding.rvShopList.adapter = adapter
    }
}