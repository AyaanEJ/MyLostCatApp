package com.example.mylostcatapp.REST

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mylostcatapp.Models.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatsRepository {

    private val url = "https://anbo-restlostcats.azurewebsites.net/api/"
    private val catsService: CatsService
    val catsLiveData: MutableLiveData<List<Cat>> = MutableLiveData<List<Cat>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        catsService = build.create(CatsService::class.java)
        getCats()
    }

    fun getCats() {
        catsService.getAllCats().enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.isSuccessful) {
                    val b: List<Cat>? = response.body()
                    catsLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun addCat(cat: Cat) {
        catsService.saveCat(cat).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                    getCats()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun deleteCat(id: Int) {
        catsService.deleteCat(id).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Deleted: " + response.body())
                    updateMessageLiveData.postValue("Updated: " + response.body())
                    getCats()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }
            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
}
