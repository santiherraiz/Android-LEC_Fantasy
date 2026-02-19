package com.example.lec_fantasy.ui.auth

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
            val enteredUser = binding.usernameEditText.text.toString()
            val enteredPass = binding.passwordEditText.text.toString()

            if (enteredUser.isNotEmpty() && enteredPass.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("user", enteredUser)
                    putString("pass", enteredPass)
                    apply()
                }
                Toast.makeText(this, getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToLoginTextView.setOnClickListener {
            finish()
        }
    }
}