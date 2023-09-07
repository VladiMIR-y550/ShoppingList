package com.smartmir.shoppinglist.presenter.shop_item_screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
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

        if (isModeEdit()) {
            val shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, DEFAULT_SHOP_ITEM_ID)
            if (shopItemId != DEFAULT_SHOP_ITEM_ID) {
                viewModel.getShopItemId(shopItemId)
            }
        }

        setupOnSaveClick()

        viewModel.currentShopItem.observe(this) {
            binding.etName.text = SpannableStringBuilder.valueOf(it.name)
            binding.etCount.text = SpannableStringBuilder.valueOf(it.count.toString())
        }

        viewModel.shouldCloseScreen.observe(this) {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.errorInputName.observe(this) {

        }

        viewModel.errorInputCount.observe(this) {

        }
    }

    private fun setupOnSaveClick() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text
            val count = binding.etCount.text
            if (isModeEdit()) {
                viewModel.editShopItem(inputName = name?.toString(), inputCount = count?.toString())

            } else {
                viewModel.addShopItem(inputName = name?.toString(), inputCount = count?.toString())
            }
        }
    }

    private fun isModeEdit(): Boolean {
        return intent.getStringExtra(EXTRA_SCREEN_MODE) == MODE_EDIT
    }

    override fun onDestroy() {
        initialBinding = null
        super.onDestroy()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val DEFAULT_SHOP_ITEM_ID = -1

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}