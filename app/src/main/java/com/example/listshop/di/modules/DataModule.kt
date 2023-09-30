package com.example.listshop.di.modules

import android.app.Application
import com.example.listshop.data.ShopListDao
import com.example.listshop.data.ShopListDatabase
import com.example.listshop.data.ShopListMapper
import com.example.listshop.data.ShopListRepositoryImpl
import com.example.listshop.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRepository(shopListDao: ShopListDao,mapper: ShopListMapper): ShopListRepository =
        ShopListRepositoryImpl(shopListDao,mapper)

    @Provides
    fun provideDao(application: Application): ShopListDao {
        return ShopListDatabase.getInstance(application).getShopListDao()
    }

    @Provides
    fun provideMapper() = ShopListMapper()

}