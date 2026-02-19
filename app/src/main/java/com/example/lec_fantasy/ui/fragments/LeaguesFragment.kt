package com.example.lec_fantasy.ui.fragments

import android.app.AlertDialog
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLeagues = view.findViewById(R.id.rvLeagues)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        val btnCreateLeague = view.findViewById<Button>(R.id.btnCreateLeague)
        val btnJoinLeague = view.findViewById<Button>(R.id.btnJoinLeague)

        rvLeagues.layoutManager = LinearLayoutManager(requireContext())
        adapter = LeagueAdapter(MockDatabase.myLeagues) { leagueSeleccionada ->
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
                    MockDatabase.allGlobalLeagues.add(nuevaLiga) // La subimos a la red global
                    MockDatabase.myLeagues.add(nuevaLiga)        // Nos la guardamos en nuestra lista
                    adapter.notifyDataSetChanged()
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

                // Buscamos si existe alguna liga con ese código
                val ligaEncontrada = MockDatabase.allGlobalLeagues.find { it.code == codigoIntroducido }

                if (ligaEncontrada != null) {
                    // Comprobamos que no estemos ya dentro
                    if (MockDatabase.myLeagues.contains(ligaEncontrada)) {
                        Toast.makeText(requireContext(), "Ya estás en esta liga", Toast.LENGTH_SHORT).show()
                    } else {
                        // Aquí, en una app real, aumentaríamos los 'participants' de la liga. De momento solo la añadimos a la lista del usuario.
                        MockDatabase.myLeagues.add(ligaEncontrada)
                        adapter.notifyDataSetChanged()
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
        if (MockDatabase.myLeagues.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            rvLeagues.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            rvLeagues.visibility = View.VISIBLE
        }
    }

    private fun entrarALaLiga(league: League) {
        // Guardamos en la base de datos que "hemos entrado" a esta liga específica
        MockDatabase.currentLeague = league

        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.selectedItemId = R.id.navigation_team
    }
}