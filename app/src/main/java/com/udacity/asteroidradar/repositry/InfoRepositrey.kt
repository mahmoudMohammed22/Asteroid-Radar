package com.udacity.asteroidradar.repositry

import android.util.Log
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.roomDatabase.AsteroidDatabase
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabase
import com.udacity.asteroidradar.networkpicture.ApiPictureNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class InfoRepositrey(val database: AsteroidDatabase) {

    val InfoPlantary = database.asteriodDao().getInfoPlantary()

    val dataToday = database.asteriodDao().getTodayData(time())



    fun time() : String{
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val timeNow = dateFormat.format(currentTime)
        return timeNow
    }

    fun savenDay():String{
        val calendar = Calendar.getInstance()
        var time = ""
        for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT)
            time = dateFormat.format(currentTime)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return time
    }


    suspend fun refrechVideos(){
        withContext(Dispatchers.IO){
            val resultInfo = ApiPictureNetwork.PictureServier
                .getInfo(time(),savenDay(),Constants.API_KEY)
            val result = JSONObject(resultInfo)
            val listResultParse = parseAsteroidsJsonResult(result)
            Log.d("time","${listResultParse}")
            database.asteriodDao().insertAll(listResultParse.asDatabase())
            database.asteriodDao().delete(time())
        }


    }


}