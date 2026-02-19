package com.example.lec_fantasy.ui.auth // Asegúrate de que el paquete es correcto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lec_fantasy.R
import com.example.lec_fantasy.databinding.ActivityMainBinding
import com.example.lec_fantasy.ui.main.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkCredentials()

        binding.signInButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val enteredUser = binding.usernameEditText.text.toString()
            val enteredPass = binding.passwordEditText.text.toString()

            // Buscamos la contraseña guardada PARA ESE USUARIO en concreto
            val savedPass = sharedPreferences.getString(enteredUser, null)

            if (savedPass != null && savedPass == enteredPass) {
                // AQUÍ: Guardamos que la sesión está iniciada Y quién es el usuario activo
                sharedPreferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("currentUser", enteredUser)
                    .apply()

                Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToRegisterTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkCredentials() {
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val currentUser = sharedPreferences.getString("currentUser", null)

        if (isLoggedIn && currentUser != null) {
            Toast.makeText(this, getString(R.string.welcome_back, currentUser), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}