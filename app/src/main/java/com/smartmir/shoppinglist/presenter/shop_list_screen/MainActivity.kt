package com.smartmir.shoppinglist.presenter.shop_list_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smartmir.shoppinglist.databinding.ActivityMainBinding
import com.smartmir.shoppinglist.domain.ShopItem
import com.smartmir.shoppinglist.presenter.shop_item_screen.ShopItemActivity

class MainActivity : AppCompatActivity() {

    private var initialBinding: ActivityMainBinding? = null
    private val binding get() = initialBinding!!
    private lateinit var viewModel: MainViewModel
    private val adapter = ShopListAdapter(
        onShopItemLongClickListener = { setupOnShopItemLongClick(it) },
        onShopItemClickListener = { setupOnShopItemClick(it.id) }
    )
    private val swipeToDeleteCallback: ItemTouchHelper.SimpleCallback = initSimpleCallback(
        onSwipeLeftOrRight = { viewModel.deleteShopItem(it) }
    )
    private val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupOnAddButtonClick()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { items ->
            adapter.submitList(items)
        }
    }

    private fun setupRecyclerView() {
        binding.rvShopList.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
    }

    private fun setupOnShopItemClick(shopItemId: Int) {
        startActivity(ShopItemActivity.newIntentEditItem(this, shopItemId))
    }

    private fun setupOnAddButtonClick() {
        binding.fabAddShopItem.setOnClickListener {
            startActivity(ShopItemActivity.newIntentAddItem(this))
        }
    }

    private fun setupOnShopItemLongClick(shopItem: ShopItem) {
        viewModel.changeEnabledShopItem(shopItem)
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
                val item = adapter.currentList[viewHolder.adapterPosition]
                onSwipeLeftOrRight(item)
            }
        }
    }

    override fun onDestroy() {
        initialBinding = null
        super.onDestroy()
    }
}