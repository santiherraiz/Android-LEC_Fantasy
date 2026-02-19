package com.example.lec_fantasy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lec_fantasy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Comprobar credenciales al iniciar
        checkCredentials()

        binding.signInButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val user = sharedPreferences.getString("user", null)
            val pass = sharedPreferences.getString("pass", null)
            val enteredUser = binding.usernameEditText.text.toString()
            val enteredPass = binding.passwordEditText.text.toString()

            if (enteredUser == user && enteredPass == pass) {
                // AQUÍ: Guardamos que la sesión está iniciada
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
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
        val user = sharedPreferences.getString("user", null)

        // AQUÍ: Ahora comprobamos el interruptor isLoggedIn
        if (isLoggedIn && user != null) {
            Toast.makeText(this, getString(R.string.welcome_back, user), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}