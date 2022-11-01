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

    fun reload() {
        repository.getCats()
    }

    operator fun get(index: Int): Cat? {
        return catsLiveData.value?.get(index)
    }

    fun add(cat: Cat) {
        repository.addCat(cat)
    }

    fun delete(id: Int) {
        repository.deleteCat(id)
    }

}