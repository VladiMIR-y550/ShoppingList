package com.smartmir.shoppinglist.presenter.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smartmir.shoppinglist.domain.ShopItem

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBind(shopItem: ShopItem)

}