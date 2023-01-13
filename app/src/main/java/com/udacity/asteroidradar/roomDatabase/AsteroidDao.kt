package com.udacity.asteroidradar.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    //toGet data from entinty
    @Query("SELECT *FROM Asteroidentitry ORDER BY closeApproachDate ASC")
    fun getInfoPlantary() :LiveData<List<AsteroidEntitry>>


    @Query("SELECT *FROM AsteroidEntitry WHERE closeApproachDate = :data")
    fun getTodayData(data:String):LiveData<List<AsteroidEntitry>>


    @Query("DELETE from AsteroidEntitry WHERE closeApproachDate < :data")
    fun delete(data:String)

    //toInsert Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun insertAll (item:List<AsteroidEntitry>)

}