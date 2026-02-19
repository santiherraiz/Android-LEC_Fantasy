package com.example.lec_fantasy.models

import java.util.UUID

data class League(
    val name: String,
    val participants: Int = 1,
    val maxParticipants: Int = 10,
    val budgetMillions: Double = 50.0,
    // Genera un código aleatorio de 6 letras/números (Ej: "A1B2C3")
    val code: String = UUID.randomUUID().toString().substring(0, 6).uppercase()
)