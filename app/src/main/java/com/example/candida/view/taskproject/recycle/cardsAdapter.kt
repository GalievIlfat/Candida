package com.example.candida.view.taskproject.recycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.data.card
import com.example.candida.view.taskproject.KanbanBoard
import com.example.candida.view.taskproject.TaskProjectActivity
import com.example.candida.view.taskproject.interfaces.OnItemClickListener
import com.example.candida.viewmodel.SharedViewModel
import com.example.candida.viewmodel.TaskProjectViewModel

class cardsAdapter( var cards:List<card>,val viewModelShare: SharedViewModel,val projectId:String,val ColumName:String): RecyclerView.Adapter<cardsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cradName: TextView = itemView.findViewById(R.id.card_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_card_item, parent, false)
        return cardsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.cradName.text = card.cardName
        holder.cradName.setOnClickListener {
            val selectedItem = cards[position]
            viewModelShare.selectedItemCards(selectedItem)
            val navController = Navigation.findNavController(holder.itemView)
            viewModelShare.selectedItemTasks(cards,projectId,ColumName)
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putString("projectId",projectId)
            bundle.putString("ColumName",ColumName)
            navController.navigate(R.id.action_kanbanBoard_to_cardViewColumn,bundle)
        }

    }
}