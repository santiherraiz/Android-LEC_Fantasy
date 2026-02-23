package com.example.lec_fantasy.models

import java.util.UUID

data class League(
    val name: String,
    val maxParticipants: Int = 10,
    val budgetMillions: Double = 50.0,
    val code: String = UUID.randomUUID().toString().substring(0, 6).uppercase(),


    val members: MutableList<String> = mutableListOf()
) {

    val participants: Int
        get() = members.size
}