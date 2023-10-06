package com.example.candida.view.taskproject.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.data.participant

class participanCardsAdapter(private val participantList: List<String>): RecyclerView.Adapter<participanCardsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewParticipantCard: TextView = itemView.findViewById(R.id.textViewParticipantCard)

        internal fun bind(item: String) {
            //projectCard.setOnClickListener { listener.onItemClick(itemView, adapterPosition) }
            textViewParticipantCard.text = item

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_participant_card, parent, false)
        return participanCardsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = participantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(participantList[position])
    }
}