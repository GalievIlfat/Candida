package com.example.candida.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.candida.model.data.participant
import com.example.candida.model.data.project
import com.example.candida.model.modelClass.modelProject
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class ProjectViewModel: ViewModel() {
    private val modelProjects = modelProject()
    fun getDataFromFirestore(mail: String): LiveData<List<project>> {
        val liveDataResult = MutableLiveData<List<project>>()
        modelProjects.getListProjectThatIHave(mail) {
            liveDataResult.value = it
        }
        return liveDataResult
    }

    fun addDataProject(
        projectName:String,
        mail:String
    ){
        val participants = mutableListOf<participant>()
        participants.add(participant(
            mailParticipant = mail,
            role = "owner"
        ))
        val project = project(
            projectName = projectName,
            participants = participants
        )
        modelProjects.addProject(project)
    }

    fun getProjectInfo(projectId:String): LiveData<project>{
        val liveDataResult = MutableLiveData<project>()
        modelProjects.projectInfo(projectId){
            liveDataResult.value = it
        }
        return liveDataResult
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

    fun updateProjectName(newProjectName:String, projectId:String){
        modelProjects.updateProjectName(newProjectName, projectId)
    }

    fun updateBeginDate(newBeginDate:String,projectId:String){
        modelProjects.updateBeginDate(stringToTimestamp(newBeginDate), projectId)
    }

    fun updateEndDate(newEndDate:String,projectId:String){
        modelProjects.updateEndDate(stringToTimestamp(newEndDate), projectId)

    }
    fun updateParticipant(newMailParticipant:String,newRole:String,projectId:String){
        modelProjects.updateParticipant(newMailParticipant,newRole,projectId)

    }





}