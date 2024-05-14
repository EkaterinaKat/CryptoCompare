package com.katysh.cryptocompare.di

import com.katysh.cryptocompare.data.workers.ChildWorkerFactory
import com.katysh.cryptocompare.data.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindsRefreshDataWorkerFactory(impl: RefreshDataWorker.Factory): ChildWorkerFactory
}