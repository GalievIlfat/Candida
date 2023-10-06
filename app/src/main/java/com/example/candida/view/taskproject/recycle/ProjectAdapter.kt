package com.example.candida.view.taskproject.recycle

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.data.project
import com.example.candida.view.taskproject.interfaces.OnItemClickListener


class ProjectAdapter(private val projectList: List<project>,private val listener: OnItemClickListener) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(projectList[position],listener)
    }

    override fun getItemCount() = projectList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectName: TextView = itemView.findViewById(R.id.card_name)
        val projectCard: CardView = itemView.findViewById(R.id.card_view)
        internal fun bind(item: project, listener: OnItemClickListener) {
            projectCard.setOnClickListener { listener.onItemClick(itemView, adapterPosition) }
            projectName.text = item.projectName
        }
    }
}