package com.example.candida.view.taskproject.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.checkTask
import com.example.candida.model.task
import com.example.candida.viewmodel.TaskProjectViewModel

class checkListAdapter(private val checkLists: List<checkTask>,val position1:Int,val viewModel: TaskProjectViewModel,val task: task): RecyclerView.Adapter<checkListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxTask: CheckBox = itemView.findViewById(R.id.checkBoxTask)
        val editTextTaskDescription: EditText = itemView.findViewById(R.id.editTextTaskDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_checklist_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =  checkLists.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val checkList = checkLists[position]
        holder.checkBoxTask.isChecked = checkList.check!!
        holder.editTextTaskDescription.setText(checkList.task)
        holder.checkBoxTask.setOnCheckedChangeListener { buttonView, isChecked ->
            // Установка значения isChecked в зависимости от состояния CheckBox

            if (isChecked) {
                viewModel.updateCheck(task,position1,position,true)
            } else {
                viewModel.updateCheck(task,position1,position,false)
            }
        }
    }
}