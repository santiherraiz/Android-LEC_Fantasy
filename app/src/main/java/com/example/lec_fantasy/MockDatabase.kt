package com.example.lec_fantasy

// Al usar 'object' en lugar de 'class', Kotlin se asegura de que
// solo exista UNA instancia de esta lista en toda la aplicación.
object MockDatabase {
    val myLeagues = mutableListOf<League>()
}