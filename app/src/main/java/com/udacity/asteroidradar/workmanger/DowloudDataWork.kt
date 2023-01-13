package com.udacity.asteroidradar.workmanger

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repositry.InfoRepositrey
import com.udacity.asteroidradar.roomDatabase.AsteroidDatabase
import retrofit2.HttpException

class DowloudDataWork(appContext : Context,params : WorkerParameters) :CoroutineWorker(appContext,params) {
    companion object{
        const val WORK_Dowloud = "DowloudDataWork"
    }


    override suspend fun doWork(): Result {
        val data = AsteroidDatabase.getDatabase(applicationContext)
        val reposirty = InfoRepositrey(data)
        return try{
            reposirty.refrechVideos()
            Result.success()
        }catch (e:HttpException){
            Result.retry()
        }
    }

}