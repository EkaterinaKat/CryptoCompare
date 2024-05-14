package com.katysh.cryptocompare.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ChildWorkerFactory {

    fun create(appContext: Context, workerParameters: WorkerParameters): ListenableWorker
}
