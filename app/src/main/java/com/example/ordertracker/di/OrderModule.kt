package com.example.ordertracker.di

import com.example.ordertracker.data.remote.OrderApi
import com.example.ordertracker.data.remote.OrdersList
import com.example.ordertracker.data.repository.OrderRepository
import com.example.ordertracker.data.repository.OrderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderModule {
    @Provides
    @Singleton
    fun provideOrderApi(): OrderApi {
        return OrdersList()
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        api: OrderApi
    ): OrderRepository {
        return OrderRepositoryImpl(api)
    }
}