package com.katysh.cryptocompare

import android.app.Application
import androidx.work.Configuration
import com.katysh.cryptocompare.data.workers.CoinWorkerFactory
import com.katysh.cryptocompare.di.DaggerApplicationComponent
import javax.inject.Inject

class CryptoApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CoinWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()
}