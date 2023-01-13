package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.udacity.asteroidradar.workmanger.DowloudDataWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication :Application() {

    val itemDowloud = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        //Function to repeatingRequest
        dowloudItem()
    }

    private fun dowloudItem() {
        //to work in backthread
        itemDowloud.launch {
            dowloudInfoPlantary()
        }

    }

    private fun dowloudInfoPlantary() {
        //Constraint with wifi and charge
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED).build()

        // Every day download dat
        val toequipRequest = PeriodicWorkRequestBuilder<DowloudDataWork>(1,TimeUnit.DAYS)
            .setConstraints(constraints).build()

        //to make workManger work
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            DowloudDataWork.WORK_Dowloud,
            ExistingPeriodicWorkPolicy.KEEP,
            toequipRequest

        )
    }
}