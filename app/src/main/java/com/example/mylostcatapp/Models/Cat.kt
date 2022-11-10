package com.example.mylostcatapp.Models

import java.io.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat

data class Cat(
    val id: Int = 0,
    val name: String,
    val description: String,
    val place: String,
    val reward: Int = 0,
    val userId: String,
    val date: Long,
    val pictureUrl: String,
) : Serializable {

    override fun toString(): String {
        return "This is $name. It comes from $place. It has been missing since ${getDate()}."
    }

    fun toLongString(): String {
        return "This cat is number $id, is called $name, lives at $place and belongs to user $userId. " +
                "It is described as follows: $description, and has been missing since ${getDate()}." +
                "The reward for returning it is $reward."
    }

    private fun getDate(): String? {
        val timeStamp = Timestamp(this.date * 1000)
        val simpleDate = SimpleDateFormat("dd/M/yyyy")
        val currentDate = simpleDate.format(timeStamp)
        return currentDate
    }
}