package com.example.listshop.presentation

import android.app.Application
import com.example.listshop.di.DaggerApplicationComponent

class ShopListApplication : Application() {

    val component = DaggerApplicationComponent.factory()
        .create(this)

}