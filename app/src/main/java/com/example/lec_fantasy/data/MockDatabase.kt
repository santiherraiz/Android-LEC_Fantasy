package com.example.lec_fantasy.data

import com.example.lec_fantasy.models.League

object MockDatabase {
    // Simulamos la base de datos global de todas las ligas creadas
    val allGlobalLeagues = mutableListOf<League>()

    // La liga a la que has "entrado" al pulsar la tarjeta
    var currentLeague: League? = null
}