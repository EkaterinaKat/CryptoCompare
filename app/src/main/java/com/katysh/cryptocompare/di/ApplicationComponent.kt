package com.katysh.cryptocompare.di

import android.app.Application
import com.katysh.cryptocompare.CryptoApplication
import com.katysh.cryptocompare.presentation.CoinDetailFragment
import com.katysh.cryptocompare.presentation.PriceListActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun inject(activity: PriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    fun inject(application: CryptoApplication)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}