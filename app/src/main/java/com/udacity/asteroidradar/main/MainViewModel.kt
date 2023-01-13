package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.roomDatabase.AsteroidDatabase
import com.udacity.asteroidradar.networkpicture.ApiPictureNetwork
import com.udacity.asteroidradar.repositry.InfoRepositrey
import com.udacity.asteroidradar.roomDatabase.AsteroidEntitry
import kotlinx.coroutines.launch

enum class AsteroidStatus{LOADING,ERROR,DONE}
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // to Image storage
    private val _photo = MutableLiveData<PictureOfDay>()
    val Photo :LiveData<PictureOfDay> = _photo



    //to get database in repositry
    val database = AsteroidDatabase.getDatabase(application)
    val infoRepositrey = InfoRepositrey(database)

    //to navigate anotherScreen
    private val _navigateToDetialScreen = MutableLiveData<AsteroidEntitry>()
    val navigateToDetialScreen :LiveData<AsteroidEntitry> = _navigateToDetialScreen

    //toShow progress_bar
    private val _status = MutableLiveData<AsteroidStatus>()
    val status : LiveData<AsteroidStatus> = _status


    //to update navigateToDetialScreen
    fun displayItemTOScreen(asteroid:AsteroidEntitry){
        _navigateToDetialScreen.value = asteroid
    }

    //when return make navigateToDetialScreen is null to update anoter data
    fun diplayItemCompler(){
        _navigateToDetialScreen.value = null


    }




    init {
        pictureOfDay()
        infoPlantaryDay()


    }



    //to Download pictureOfDay From API
    private  fun pictureOfDay() {
        viewModelScope.launch {
            _status.value = AsteroidStatus.LOADING
            try {
                val result = ApiPictureNetwork.PictureServier
                    .getPicture(Constants.API_KEY)
                _photo.value = result
                _status.value = AsteroidStatus.DONE
            } catch (e:Exception){
                _status.value = AsteroidStatus.ERROR
            }
        }
    }

    //to Download infoPlantaryDay From API
    private fun infoPlantaryDay(){
        viewModelScope.launch {
            try {
                infoRepositrey.refrechVideos()
                Log.d("time", infoRepositrey.refrechVideos().toString())

            }catch (e:Exception){
                _status.value = AsteroidStatus.ERROR
            }
        }
    }

    var infoList = infoRepositrey.InfoPlantary

    //to displayList


    fun update(){
       infoList = infoRepositrey.dataToday
    }
    fun updataTWO(){
        infoList = infoRepositrey.InfoPlantary
    }




}

class MainViewModelFactory(val application: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("UNKNOWN ViewModel CLASS")
    }

}