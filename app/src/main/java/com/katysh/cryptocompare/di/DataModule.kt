package com.katysh.cryptocompare.di

import android.app.Application
import com.katysh.cryptocompare.data.database.AppDatabase
import com.katysh.cryptocompare.data.database.CoinInfoDao
import com.katysh.cryptocompare.data.repository.CryptoRepoImpl
import com.katysh.cryptocompare.domain.repo.CryptoRepo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: CryptoRepoImpl): CryptoRepo

    companion object {

        @Provides
        fun provideDao(application: Application): CoinInfoDao {
            return AppDatabase.getInstance(application).priceDao()
        }
    }
}