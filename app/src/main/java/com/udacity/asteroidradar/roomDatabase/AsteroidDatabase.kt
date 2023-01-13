package com.udacity.asteroidradar.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntitry::class], version = 1)
abstract class AsteroidDatabase :RoomDatabase() {
    abstract fun asteriodDao() : AsteroidDao

    companion object{

        @Volatile
        private var INSTANCE :AsteroidDatabase? = null

        fun getDatabase(context: Context):AsteroidDatabase{
            // if the INSTANCE is not null, then return     it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "Plantary_data"
                ).build()
                INSTANCE = instance
                instance
            }

        }


    }
}