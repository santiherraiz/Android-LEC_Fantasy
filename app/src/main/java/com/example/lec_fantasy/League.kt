package com.example.lec_fantasy

data class League(
    val name: String,
    val participants: Int = 1, // Por defecto empiezas tú solo
    val maxParticipants: Int = 10,
    val budgetMillions: Double = 50.0 // Presupuesto inicial en millones
)