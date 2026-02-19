package com.example.lec_fantasy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buscamos el botón en la vista usando su ID
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)

        // Configuramos la acción al hacer clic
        btnLogout.setOnClickListener {
            desloguearUsuario()
        }
    }

    private fun desloguearUsuario() {
        // 1. Cambiamos el estado de isLoggedIn a false en SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // En lugar de editor.clear(), solo "apagamos" la sesión activa.
        // El usuario y la contraseña siguen guardados.
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        // 2. Redirigimos al MainActivity (Pantalla de Login)
        val intent = Intent(requireActivity(), MainActivity::class.java)

        // 3. Limpiamos el historial de navegación para que no se pueda volver atrás pulsando la flecha del móvil
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}