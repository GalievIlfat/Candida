package com.example.candida.view.taskproject.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.data.participant
import com.example.candida.model.data.project
import com.example.candida.view.taskproject.interfaces.OnItemClickListener

class participantAdapter(private val participantList: List<participant>): RecyclerView.Adapter<participantAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewMail: TextView = itemView.findViewById(R.id.textViewMail)
        val textViewRuleParticipant: TextView = itemView.findViewById(R.id.textViewRuleParticipant)
        internal fun bind(item: participant) {
            //projectCard.setOnClickListener { listener.onItemClick(itemView, adapterPosition) }
            textViewMail.text = item.mailParticipant
            textViewRuleParticipant.text = item.role
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_participan_item, parent, false)
        return participantAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = participantList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(participantList[position])
    }
}