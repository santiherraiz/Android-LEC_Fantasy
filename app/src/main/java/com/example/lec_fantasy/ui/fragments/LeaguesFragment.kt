package com.example.lec_fantasy.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lec_fantasy.R
import com.example.lec_fantasy.adapters.LeagueAdapter
import com.example.lec_fantasy.data.MockDatabase
import com.example.lec_fantasy.models.League
import com.google.android.material.bottomnavigation.BottomNavigationView

class LeaguesFragment : Fragment(R.layout.fragment_leagues) {

    private lateinit var adapter: LeagueAdapter
    private lateinit var rvLeagues: RecyclerView
    private lateinit var tvEmptyState: TextView

    // Esta lista servirá para mostrar solo las ligas del usuario conectado
    private var misLigasActuales = mutableListOf<League>()

    // Recuperamos el nombre del usuario que ha iniciado sesión
    private val currentUser: String
        get() {
            val prefs = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
            // CAMBIO AQUÍ: Ahora busca "currentUser" en vez de "user"
            return prefs.getString("currentUser", "") ?: ""
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLeagues = view.findViewById(R.id.rvLeagues)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        val btnCreateLeague = view.findViewById<Button>(R.id.btnCreateLeague)
        val btnJoinLeague = view.findViewById<Button>(R.id.btnJoinLeague)

        rvLeagues.layoutManager = LinearLayoutManager(requireContext())
        adapter = LeagueAdapter(misLigasActuales) { leagueSeleccionada ->
            entrarALaLiga(leagueSeleccionada)
        }
        rvLeagues.adapter = adapter

        actualizarVista()

        btnCreateLeague.setOnClickListener { mostrarDialogoCrearLiga() }
        btnJoinLeague.setOnClickListener { mostrarDialogoUnirseLiga() }
    }

    private fun mostrarDialogoCrearLiga() {
        val input = EditText(requireContext())
        input.hint = "Ej: Los Tryhards de la LEC"

        AlertDialog.Builder(requireContext())
            .setTitle("Crear nueva liga")
            .setMessage("Introduce el nombre de tu liga:")
            .setView(input)
            .setPositiveButton("Crear") { _, _ ->
                val leagueName = input.text.toString()
                if (leagueName.isNotEmpty()) {
                    val nuevaLiga = League(name = leagueName)

                    // El usuario que la crea se une automáticamente
                    nuevaLiga.members.add(currentUser)

                    MockDatabase.allGlobalLeagues.add(nuevaLiga)
                    actualizarVista()
                } else {
                    Toast.makeText(requireContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarDialogoUnirseLiga() {
        val input = EditText(requireContext())
        input.hint = "Ej: A1B2C3"

        AlertDialog.Builder(requireContext())
            .setTitle("Unirse a una liga")
            .setMessage("Introduce el código de invitación:")
            .setView(input)
            .setPositiveButton("Unirse") { _, _ ->
                val codigoIntroducido = input.text.toString().trim().uppercase()
                val ligaEncontrada = MockDatabase.allGlobalLeagues.find { it.code == codigoIntroducido }

                if (ligaEncontrada != null) {
                    if (ligaEncontrada.members.contains(currentUser)) {
                        Toast.makeText(requireContext(), "Ya estás en esta liga", Toast.LENGTH_SHORT).show()
                    } else if (ligaEncontrada.members.size >= ligaEncontrada.maxParticipants) {
                        // Comprobación de que la liga no esté llena
                        Toast.makeText(requireContext(), "La liga está llena", Toast.LENGTH_SHORT).show()
                    } else {
                        // Añadimos al usuario a los miembros de la liga
                        ligaEncontrada.members.add(currentUser)
                        actualizarVista()
                        Toast.makeText(requireContext(), "¡Te has unido a ${ligaEncontrada.name}!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Código incorrecto o liga no encontrada", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizarVista() {
        // Vaciamos la lista visual y buscamos en la base de datos global las ligas donde aparezca nuestro usuario
        misLigasActuales.clear()
        val ligasDeEsteUsuario = MockDatabase.allGlobalLeagues.filter { it.members.contains(currentUser) }
        misLigasActuales.addAll(ligasDeEsteUsuario)

        adapter.notifyDataSetChanged()

        if (misLigasActuales.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            rvLeagues.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            rvLeagues.visibility = View.VISIBLE
        }
    }

    private fun entrarALaLiga(league: League) {
        MockDatabase.currentLeague = league
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.selectedItemId = R.id.navigation_team
    }
}