package com.example.lec_fantasy.models

import java.util.UUID

data class League(
    val name: String,
    val maxParticipants: Int = 10,
    val budgetMillions: Double = 50.0,
    val code: String = UUID.randomUUID().toString().substring(0, 6).uppercase(),

    // Lista para guardar qué usuarios ("1234", "123", etc.) están en esta liga
    val members: MutableList<String> = mutableListOf()
) {
    // Calculamos automáticamente el número de participantes según cuánta gente haya en la lista
    val participants: Int
        get() = members.size
}