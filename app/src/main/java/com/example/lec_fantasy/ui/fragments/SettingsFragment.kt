package com.example.lec_fantasy.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lec_fantasy.R
import com.example.lec_fantasy.data.MockDatabase
import com.example.lec_fantasy.ui.auth.MainActivity // O MainActivity, como lo hayas llamado
import kotlinx.coroutines.MainScope

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        val tvLeagueCode = view.findViewById<TextView>(R.id.tvLeagueCode)

        // Si hemos entrado a alguna liga, mostramos su código
        val activeLeague = MockDatabase.currentLeague
        if (activeLeague != null) {
            tvLeagueCode.text = "Código de invitación a ${activeLeague.name}:\n${activeLeague.code}"
            tvLeagueCode.visibility = View.VISIBLE
        } else {
            // Si no hemos entrado a ninguna, lo mantenemos oculto
            tvLeagueCode.visibility = View.GONE
        }

        btnLogout.setOnClickListener {
            desloguearUsuario()
        }
    }

    private fun desloguearUsuario() {
        val sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // Al desloguearnos, borramos la memoria de en qué liga estábamos
        MockDatabase.currentLeague = null

        val intent = Intent(requireActivity(), MainActivity::class.java) // Asegúrate de que el nombre coincida con tu Activity de Login
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}