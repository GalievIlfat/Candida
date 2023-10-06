package com.example.candida.view.taskproject.recycle

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.task
import com.example.candida.view.taskproject.interfaces.OnItemClickListener
import com.example.candida.viewmodel.SharedViewModel
import com.example.candida.viewmodel.TaskProjectViewModel


class ColumnAdapter(val tasks: List<task>,val viewModel: TaskProjectViewModel,val viewModelShare: SharedViewModel,val role:String): RecyclerView.Adapter<ColumnAdapter.columnViewHolder>() {
    inner class columnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recyclerViewCards: RecyclerView = itemView.findViewById(R.id.recycle_cards)
        val editTextNameColumn : EditText = itemView.findViewById(R.id.editTextNameColumn)
        val buttonMenu: ImageView = itemView.findViewById(R.id.mMenu)
        val editTextAddCards : EditText = itemView.findViewById(R.id.editTextAddCard)
        val imageViewAddCard : ImageView = itemView.findViewById(R.id.imageViewAddCard)
        val linearAddCards: LinearLayout = itemView.findViewById(R.id.linearAddCards)

        init{
            buttonMenu.setOnClickListener { PopupMenu(it) }
        }

        private fun PopupMenu(it: View) {
            val popupMenus = PopupMenu(buttonMenu.context,it)
            popupMenus.inflate(R.menu.ic_menu)
            popupMenus.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.AddCard -> {
                        Log.d("Menu","Menu is working")
                        //TODO("Доделать когда нибудь эту функцию")

                    }
                }
                true
            }
            popupMenus.gravity = Gravity.LEFT
            popupMenus.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): columnViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycle_column_item, parent, false)
        return columnViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: columnViewHolder, position: Int) {
        val task = tasks[position]
       // holder.textViewTitle.text = column.title
       // holder.recyclerViewCards.adapter= holder.cardsAdapter
        val cardAdapter = cardsAdapter(task.cards,viewModelShare,task.projectID.toString(),task.nameColumn.toString())
        holder.recyclerViewCards.adapter = cardAdapter
        //holder.recyclerViewCards.layoutManager = LinearLayoutManager()
        val nameColumn = task.nameColumn.toString()
        holder.editTextNameColumn.setText(nameColumn)
        holder.editTextNameColumn.clearFocus()
        val cardLayoutManager = LinearLayoutManager(holder.recyclerViewCards.context,LinearLayoutManager.VERTICAL, false)
        holder.recyclerViewCards.layoutManager = cardLayoutManager
        //viewModelShare.selectedItemTasks(task)

        holder.imageViewAddCard.setOnClickListener {
            if(task.projectID!=null && task.nameColumn != null && !holder.editTextAddCards.text.toString().isNullOrEmpty())
            {
                viewModel.addCardInColumn(task.projectID,task.nameColumn,holder.editTextAddCards.text.toString())
            }
        }
        if(role == "editor" || role == "viewer"){
            holder.linearAddCards.visibility = View.GONE
        }


    }
}