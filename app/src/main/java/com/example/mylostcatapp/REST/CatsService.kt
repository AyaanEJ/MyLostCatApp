package com.example.mylostcatapp.REST

import com.example.mylostcatapp.Models.Cat
import retrofit2.Call
import retrofit2.http.*

    interface CatsService {
        @GET("cats")
        fun getAllCats(): Call<List<Cat>>

        @POST("cats")
        fun saveCat(@Body cat: Cat): Call<Cat>

        @DELETE("cats/{id}")
        fun deleteCat(@Path("id") id: Int): Call<Cat>
    }
