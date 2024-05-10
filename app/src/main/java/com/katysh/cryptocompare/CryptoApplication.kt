package com.katysh.cryptocompare

import android.app.Application
import com.katysh.cryptocompare.di.DaggerApplicationComponent

class CryptoApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}