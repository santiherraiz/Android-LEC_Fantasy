package com.example.lec_fantasy.data

import com.example.lec_fantasy.models.League

object MockDatabase {
    // Simulamos la base de datos global de todas las ligas creadas por cualquier usuario
    val allGlobalLeagues = mutableListOf<League>()

    // Las ligas a las que tú (el usuario actual) perteneces
    val myLeagues = mutableListOf<League>()

    // La liga a la que has "entrado" al pulsar la tarjeta (para saber cuál mostrar en ajustes)
    var currentLeague: League? = null
}