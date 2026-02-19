package com.example.lec_fantasy.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lec_fantasy.ui.fragments.LeaguesFragment
import com.example.lec_fantasy.ui.fragments.MarketFragment
import com.example.lec_fantasy.R
import com.example.lec_fantasy.ui.fragments.RankingFragment
import com.example.lec_fantasy.ui.fragments.SettingsFragment
import com.example.lec_fantasy.ui.fragments.TeamFragment
import com.example.lec_fantasy.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                LeaguesFragment()
            ).commit()
        }

        binding.navView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_leagues -> selectedFragment = LeaguesFragment()
                R.id.navigation_ranking -> selectedFragment = RankingFragment()
                R.id.navigation_team -> selectedFragment = TeamFragment()
                R.id.navigation_market -> selectedFragment = MarketFragment()
                R.id.navigation_settings -> selectedFragment = SettingsFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            }
            true
        }
    }
}