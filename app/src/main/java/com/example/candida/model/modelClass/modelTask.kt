package com.example.candida.model.modelClass

import android.util.Log
import androidx.compose.foundation.layout.Column
import com.example.candida.model.checkTask
import com.example.candida.model.data.card
import com.example.candida.model.data.project
import com.example.candida.model.task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject


class modelTask {
    private val db = FirebaseFirestore.getInstance()
    private val collectionProject = db.collection("Task")

    fun getListTaskByID(id: String, callback: (List<task>?) -> Unit){
            collectionProject
                .whereEqualTo("projectID", id)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        //Log.w(TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    val tasks = mutableListOf<task>()
                    for (doc in value!!) {
                        doc.toObject(task::class.java).let{ task->
                            tasks.add(task)
                        }
                    }
                    callback(tasks)
                }
    }

    fun getTaskIdFromColumnName(projectId: String, columnName: String,callback: (String)->Unit) {
       collectionProject.whereEqualTo("nameColumn", columnName)
                .whereEqualTo("projectID", projectId)
                .limit(1).get().addOnSuccessListener { result ->
            for (document in result) {
                //Log.d("getTaskIdFromColumnName", document.id)
                    callback(document.id)
                break
            }
        }.addOnFailureListener { exception ->

        }
    }

    fun AddCard(projectId:String,columnName: String,cardName:String){
        val projectRef = collectionProject.document(projectId)
        projectRef.get().addOnSuccessListener{
            Log.d("Cards", it.data.toString())
        }

        getTaskIdFromColumnName(projectId,columnName){taskId->
            val card = card(
                cardName = cardName
            )
            collectionProject.document(taskId)
                .update("cards", FieldValue.arrayUnion(card))
        }
    }

    fun AddColumn(projectId:String,nameColumn: String){
        val task = task(
            projectID = projectId,
            nameColumn = nameColumn
        )
        collectionProject.add(task)
            .addOnSuccessListener {
                Log.d("Column", task.nameColumn.toString() + " " + task.projectID.toString())
            }
    }

    fun updateCardName(task:task,position:Int,cardName: String){
        var cardUpdate = task.cards
        cardUpdate[position].cardName = cardName
        getTaskIdFromColumnName(task.projectID.toString(),task.nameColumn.toString()){
            collectionProject.document(it)
                .update("cards",cardUpdate)
        }
    }
    fun updateBeginDate(task:task,position:Int, beginDate:Timestamp){
        var cardUpdate = task.cards
        cardUpdate[position].startDateCard = beginDate
        getTaskIdFromColumnName(task.projectID.toString(),task.nameColumn.toString()){
            collectionProject.document(it)
                .update("cards",cardUpdate)
        }
    }

    fun updateEndDate(task:task,position:Int, endDate:Timestamp){
        var cardUpdate = task.cards
        cardUpdate[position].endDateCard = endDate
        getTaskIdFromColumnName(task.projectID.toString(),task.nameColumn.toString()){
            collectionProject.document(it)
                .update("cards",cardUpdate)
        }
    }

    fun updateParticipant(task:task,position:Int, email:String){
        var cardUpdate = task.cards
        cardUpdate[position].participantsCard?.add(email)
        getTaskIdFromColumnName(task.projectID.toString(),task.nameColumn.toString()){
            collectionProject.document(it)
                .update("cards",cardUpdate)
        }
    }

    fun updateTask(task:task,position:Int, taskDescription:String){
        var cardUpdate = task.cards
        cardUpdate[position].checkList?.add(checkTask(task=taskDescription,check = false))
        getTaskIdFromColumnName(task.projectID.toString(),task.nameColumn.toString()){
            collectionProject.document(it)
                .update("cards",cardUpdate)
        }
    }

    fun updateCheck(task:task,position1:Int, position2:Int,checks:Boolean){
        var cardUpdate = task.cards
        cardUpdate[position1].checkList!![position2].check = checks
        getTaskIdFromColumnName(task.projectID.toString(),task.nameColumn.toString()){
            collectionProject.document(it)
                .update("cards",cardUpdate)
        }
    }

    fun projectInfo(projectId: String,projectName:String,callback: (task) -> Unit){
        getTaskIdFromColumnName(projectId,projectName){
            collectionProject.document(it)
                .get()
                .addOnSuccessListener {
                    callback(it.toObject<task>()!!)
                }
        }
    }
    fun checkList(projectId: String,projectName:String,position: Int,callback: (List<checkTask>) -> Unit){
        getTaskIdFromColumnName(projectId,projectName){
            collectionProject.document(it)
                .get()
                .addOnSuccessListener {
                    val task = it.toObject<task>()!!
                    callback(task.cards[position].checkList as List<checkTask>)
                }
        }
    }
    fun participantCard(projectId: String,projectName: String,position: Int,callback: (List<String>) -> Unit){
        getTaskIdFromColumnName(projectId,projectName){
            collectionProject.document(it)
                .get()
                .addOnSuccessListener {
                    val task = it.toObject<task>()!!
                    callback(task.cards[position].participantsCard as List<String>)
                }
        }
    }


}