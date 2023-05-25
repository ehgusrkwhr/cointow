package com.example.cointwo.data.di

import com.example.cointwo.data.repository.CoinSocketRepository
import com.example.cointwo.data.repository.CoinSocketRepositoryImpl
import com.example.cointwo.data.repository.TickerRepository
import com.example.cointwo.data.repository.TickerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTickerRepository(tickerRepository: TickerRepositoryImpl) : TickerRepository

    @Binds
    @Singleton
    abstract fun bindCoinSocketRepository(coinSocketRepository: CoinSocketRepositoryImpl) : CoinSocketRepository

}