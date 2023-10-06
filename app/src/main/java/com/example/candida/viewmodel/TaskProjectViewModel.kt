package com.example.candida.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.candida.model.checkTask
import com.example.candida.model.data.project
import com.example.candida.model.modelClass.modelProject
import com.example.candida.model.modelClass.modelTask
import com.example.candida.model.task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.toObject
import java.text.SimpleDateFormat
import java.util.*

class TaskProjectViewModel: ViewModel() {
    private val modelTask = modelTask()
    private val modelProject = modelProject()

    fun getKanbanBoard(project: project): LiveData<List<task>> {
        val liveDataResult = MutableLiveData<List<task>>()
        modelProject.getProjectID(project){id->
            modelTask.getListTaskByID(id){
                    liveDataResult.value = it
            }
        }
        return liveDataResult
    }
    fun addCardInColumn(projectId:String,columnName: String,cardName:String){
        modelTask.AddCard(projectId,columnName,cardName)
    }

    fun addColumn(projectId:String,columnName: String){
        modelTask.AddColumn(projectId,columnName)
    }

    fun updateTask(task:task,position:Int, taskDescription:String){
        modelTask.updateTask(task,position,taskDescription)
    }

    fun updateParticipant(task:task,position:Int, email:String){
        modelTask.updateParticipant(task,position,email)
    }
    fun updateCardName(task:task,position:Int,cardName: String){
        modelTask.updateCardName(task,position,cardName)
    }

    fun timestampConvert(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        return dateFormatter.format(date)
    }

    fun stringToTimestamp(dateStr: String): Timestamp {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val parsedDate = dateFormat.parse(dateStr)
        val timestamp = Timestamp(parsedDate.time / 1000, (parsedDate.time % 1000).toInt() * 1000000)
        return timestamp
    }

    fun updateBeginDate(task:task,position:Int, beginDate: String){
        modelTask.updateBeginDate(task,position,stringToTimestamp(beginDate))
    }
    fun updateEndDate(task:task,position:Int, endDate:String){
        modelTask.updateEndDate(task,position,stringToTimestamp(endDate))
    }

    fun getInfoTask(projectId:String,columnName: String):LiveData<task>{
        val liveDataResult = MutableLiveData<task>()
        modelTask.projectInfo(projectId,columnName){
            liveDataResult.value = it
        }
        return liveDataResult
    }

    fun getParticipantsCard(projectId:String,columnName: String,position: Int): LiveData<List<String>>{
        val liveDataResult = MutableLiveData<List<String>>()
        modelTask.participantCard(projectId,columnName,position){
            liveDataResult.value = it
        }
        return liveDataResult
    }

    fun getCheckList(projectId:String,columnName: String,position: Int): LiveData<List<checkTask>>{
        val liveDataResult = MutableLiveData<List<checkTask>>()
        modelTask.checkList(projectId,columnName,position){
            liveDataResult.value = it
        }
        return liveDataResult
    }

    fun updateCheck(task:task,position1:Int, position2:Int,checks:Boolean){
        modelTask.updateCheck(task,position1,position2, checks)
    }


}