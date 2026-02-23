package com.example.lec_fantasy.data

import android.content.Context
import com.example.lec_fantasy.models.League
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MockDatabase {
    // Simulamos la base de datos global de todas las ligas creadas
    val allGlobalLeagues = mutableListOf<League>()

    // La liga a la que has "entrado" al pulsar la tarjeta
    var currentLeague: League? = null

    fun save(context: Context) {
        val prefs = context.getSharedPreferences("lec_fantasy_db", Context.MODE_PRIVATE)
        val json = Gson().toJson(allGlobalLeagues)
        prefs.edit().putString("leagues", json).apply()
    }

    fun load(context: Context) {
        val prefs = context.getSharedPreferences("lec_fantasy_db", Context.MODE_PRIVATE)
        val json = prefs.getString("leagues", null)
        if (json != null) {
            val type = object : TypeToken<List<League>>() {}.type
            val leagues: List<League> = Gson().fromJson(json, type)
            allGlobalLeagues.clear()
            allGlobalLeagues.addAll(leagues)
        }
    }
}
