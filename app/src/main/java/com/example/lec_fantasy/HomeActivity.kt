package com.example.lec_fantasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lec_fantasy.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar el fragmento inicial por defecto (Ligas)
        if (savedInstanceState == null) {
            replaceFragment(LeaguesFragment())
        }

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_leagues -> {
                    replaceFragment(LeaguesFragment())
                    true
                }
                R.id.navigation_ranking -> {
                    replaceFragment(RankingFragment())
                    true
                }
                R.id.navigation_team -> {
                    replaceFragment(TeamFragment())
                    true
                }
                R.id.navigation_market -> {
                    replaceFragment(MarketFragment())
                    true
                }
                R.id.navigation_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}