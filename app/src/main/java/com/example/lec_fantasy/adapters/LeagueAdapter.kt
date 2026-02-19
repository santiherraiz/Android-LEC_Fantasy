package com.example.lec_fantasy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lec_fantasy.R
import com.example.lec_fantasy.models.League

class LeagueAdapter(
    private val leagues: List<League>,
    private val onLeagueClick: (League) -> Unit
) : RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvLeagueName)
        val tvParticipants: TextView = view.findViewById(R.id.tvParticipants)
        val tvBudget: TextView = view.findViewById(R.id.tvBudget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_league, parent, false)
        return LeagueViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val league = leagues[position]
        holder.tvName.text = league.name
        holder.tvParticipants.text = "${league.participants}/${league.maxParticipants}"
        holder.tvBudget.text = "Presupuesto: ${league.budgetMillions}M"

        // Detectar el clic en la tarjeta
        holder.itemView.setOnClickListener {
            onLeagueClick(league)
        }
    }

    override fun getItemCount() = leagues.size
}