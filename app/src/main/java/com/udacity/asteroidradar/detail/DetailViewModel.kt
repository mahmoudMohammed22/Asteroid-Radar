package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.roomDatabase.AsteroidEntitry

class DetailViewModel(asteroid :AsteroidEntitry , application:Application) :AndroidViewModel(application) {
    private val _navgiateFromScreen = MutableLiveData<AsteroidEntitry>()
    val navgiateFromScreen : LiveData<AsteroidEntitry> = _navgiateFromScreen

    init {
        _navgiateFromScreen.value = asteroid
    }


}
class DetailViewModelFactory(
    private val asteroid :AsteroidEntitry,
    private val application :Application ) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(asteroid,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

