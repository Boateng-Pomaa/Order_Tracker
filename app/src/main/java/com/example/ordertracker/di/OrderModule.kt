package com.example.ordertracker.di

import com.example.ordertracker.data.repository.CustomerRepository
import com.example.ordertracker.data.repository.CustomerRepositoryImpl
import com.example.ordertracker.data.repository.OrderRepository
import com.example.ordertracker.data.repository.OrderRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(
        impl: CustomerRepositoryImpl
    ): CustomerRepository {
        return impl
    }
}
