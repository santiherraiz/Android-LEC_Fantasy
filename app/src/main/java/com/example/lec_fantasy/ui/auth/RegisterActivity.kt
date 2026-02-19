package com.example.lec_fantasy.ui.auth

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lec_fantasy.R
import com.example.lec_fantasy.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val usernameIngresado = binding.usernameEditText.text.toString()
            val passwordIngresado = binding.passwordEditText.text.toString()

            if (usernameIngresado.isNotEmpty() && passwordIngresado.isNotEmpty()) {
                // Dentro del setOnClickListener de tu botón de registro:
                val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                // En lugar de poner "user" y "pass", usamos el nombre del usuario como llave
                // Ejemplo: Si el usuario es "123", guardará la clave "123" con su contraseña.
                editor.putString(usernameIngresado, passwordIngresado)
                editor.apply()

                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                finish() // Volvemos al login
            } else {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToLoginTextView.setOnClickListener {
            finish()
        }
    }
}