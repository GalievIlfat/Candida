package com.example.candida.model.modelClass

import android.util.Log
import com.example.candida.model.data.participant
import com.example.candida.model.data.project
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import java.lang.reflect.Field

class modelProject {
    private val db = FirebaseFirestore.getInstance()
    private val collectionProject = db.collection("Project")

    fun getListProjectThatIHave(mail:String,callback: (List<project>) -> Unit){
        collectionProject
            .addSnapshotListener { value, error ->
                if (error != null) {
                    //Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }
                var projects = mutableListOf<project>()
                for (doc in value!!) {
                    doc.toObject(project::class.java).let{project->
                        project.participants!!.forEach {
                            if(it.mailParticipant == mail){
                                projects.add(project)
                                Log.d("model",project.toString())
                                Log.d("model", "-------------------------------")
                                projects.forEach(){
                                    Log.d("model",it.toString())
                                }

                            }

                        }

                    }
                }
                callback(projects)
            }

    }
    fun getProjectID(myProject: project,callback: (String) -> Unit){
        collectionProject
            .addSnapshotListener { value, error ->
                if (error != null) {
                    //Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    doc.toObject(project::class.java).let{project->
                        if(project == myProject){
                            callback(doc.id)

                        }

                    }
                }
            }

    }
    fun addProject(project:project){

        var projectId = ""
        getProjectID(project){
            projectId = it
        }
        if(projectId=="") projectId = collectionProject.document().id
        collectionProject.document(projectId)
            .set(project, SetOptions.merge())
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->

            }
    }

    fun projectInfo(projectId:String,callback: (project) -> Unit){
        collectionProject.document(projectId)
            .get()
            .addOnSuccessListener {
                callback(it.toObject<project>()!!)
            }

    }

    fun updateProjectName(newProject:String,projectId: String){
        collectionProject.document(projectId)
            .update("projectName",newProject)
    }

    fun updateBeginDate(newBegin:Timestamp,projectId: String){
        collectionProject.document(projectId)
            .update("startDate",newBegin)
    }

    fun updateEndDate(newEnd:Timestamp,projectId: String){
        collectionProject.document(projectId)
            .update("endDate",newEnd)
    }

    fun updateParticipant(newMailParticipant:String,newRole:String,projectId: String){
        val participant = participant(mailParticipant = newMailParticipant, role = newRole)
        collectionProject.document(projectId)
            .update("participants",FieldValue.arrayUnion(participant))
    }

    fun getRules(projectId: String,email:String, callback: (String) -> Unit){
        collectionProject
            .addSnapshotListener { value, error ->
                if (error != null) {
                    //Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }
                for (doc in value!!) {
                    doc.toObject(project::class.java).let{project->
                        project.participants?.forEach(){
                            if(it.mailParticipant == email){
                                callback(it.role!!)
                            }
                        }

                    }
                }
            }
    }

}