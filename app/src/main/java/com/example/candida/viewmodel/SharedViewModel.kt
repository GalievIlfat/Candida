package com.example.candida.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.candida.model.data.card
import com.example.candida.model.data.project
import com.example.candida.model.data.users
import com.example.candida.model.modelClass.modelUsers
import com.example.candida.model.task

class SharedViewModel : ViewModel() {
    val selectedItemProject = MutableLiveData<project>()
    val selectedItemUser = MutableLiveData<users>()
    val selectedItemCard = MutableLiveData<card>()
    val selectedItemTask = MutableLiveData<task>()
    val selectedItemRule = MutableLiveData<String>()

    fun selectItem(project: project) {
        selectedItemProject.value = project
    }

    fun selectedItemUsers(users: users){
        selectedItemUser.value = users
    }

    fun selectedItemCards(card: card){
        selectedItemCard.value = card
    }

    fun selectedItemTasks(card: List<card>,projectId:String,ColumnName:String){
        val task = task(
        cards = card,
        projectID = projectId,
         nameColumn = ColumnName
        )
        selectedItemTask.value = task
    }

    fun selectedItemRules(rule:String){
        selectedItemRule.value = rule
    }
}