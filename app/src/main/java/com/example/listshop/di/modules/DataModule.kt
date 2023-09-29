package com.example.listshop.di.modules

import com.example.listshop.data.ShopListMapper
import com.example.listshop.data.ShopListRepositoryImpl
import com.example.listshop.domain.ShopListRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideRepository(repositoryImpl: ShopListRepositoryImpl): ShopListRepository



}