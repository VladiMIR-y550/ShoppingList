package com.smartmir.shoppinglist.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smartmir.shoppinglist.databinding.ActivityMainBinding
import com.smartmir.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private var initialBinding: ActivityMainBinding? = null
    private val binding get() = initialBinding!!
    private lateinit var viewModel: MainViewModel
    private val adapter = ShopListAdapter(
        onShopItemLongClickListener = { viewModel.changeEnabledShopItem(it) },
        onShopItemClickListener = { Log.d("MainActivity", "OnShopItemClickListener click $it") }
    )
    private val swipeToDeleteCallback: ItemTouchHelper.SimpleCallback = initSimpleCallback(
        onSwipeLeftOrRight = { viewModel.deleteShopItem(it) }
    )
    private val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { items ->
            adapter.items = items
        }
    }

    private fun setupView() {
        binding.rvShopList.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
    }

    private fun initSimpleCallback(
        onSwipeLeftOrRight: (
            item: ShopItem
        ) -> Unit
    ): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.items[viewHolder.adapterPosition]
                onSwipeLeftOrRight(item)
            }
        }
    }
}