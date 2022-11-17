package com.example.mylostcatapp.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mylostcatapp.REST.CatsRepository

//import com.example.mylostcatapp.REST.


class CatsViewModel : ViewModel() {
    private val repository = CatsRepository()

    // private
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }


    //private??
    fun reload() {
        repository.getCats()
    }

    // get specific cat
    operator fun get(index: Int): Cat? {
        return catsLiveData.value?.get(index)
    }

    fun add(cat: Cat) {
        repository.addCat(cat)
    }

    fun delete(id: Int) {
        repository.deleteCat(id)
    }
    fun sortByReward() {
        repository.sortByReward()
    }

    //sorting methods
    fun sortByRewardDescending() {
        repository.sortByRewardDescending()
    }

    fun sortByDate() {
        repository.sortByDate()
    }

    fun sortByDateDescending() {
        repository.sortByDateDescending()
    }

    // filtering methods
    fun filterByPlace(place: String) {
        repository.filterByPlace(place)
    }

    fun filterByReward(reward: Int) {
        repository.filterByReward(reward)
    }
}

