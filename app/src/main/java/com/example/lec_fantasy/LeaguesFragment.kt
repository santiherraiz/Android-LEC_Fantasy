package com.example.lec_fantasy

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

        // Configurar el RecyclerView usando la lista global de MockDatabase
        rvLeagues.layoutManager = LinearLayoutManager(requireContext())
        adapter = LeagueAdapter(MockDatabase.myLeagues) { leagueSeleccionada ->
            entrarALaLiga(leagueSeleccionada)
        }
        rvLeagues.adapter = adapter

        actualizarVista()

        // Botón Crear Liga
        btnCreateLeague.setOnClickListener {
            mostrarDialogoCrearLiga()
        }

        // Botón Unirse a Liga
        btnJoinLeague.setOnClickListener {
            Toast.makeText(requireContext(), "Función de unirse próximamente", Toast.LENGTH_SHORT).show()
        }
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

                    // Añadimos la liga a nuestra "base de datos" global
                    MockDatabase.myLeagues.add(nuevaLiga)

                    adapter.notifyDataSetChanged()
                    actualizarVista()
                } else {
                    Toast.makeText(requireContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizarVista() {
        // Muestra el mensaje de vacío si no hay ligas, o la lista si las hay
        if (MockDatabase.myLeagues.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            rvLeagues.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            rvLeagues.visibility = View.VISIBLE
        }
    }

    private fun entrarALaLiga(league: League) {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNav.selectedItemId = R.id.navigation_team
    }
}