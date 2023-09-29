package com.example.listshop.di

import android.app.Activity
import android.app.Application
import com.example.listshop.di.modules.DataModule
import com.example.listshop.di.modules.DomainModule
import com.example.listshop.presentation.MainActivity
import com.example.listshop.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier


@Component(modules = [DataModule::class, DomainModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }


}