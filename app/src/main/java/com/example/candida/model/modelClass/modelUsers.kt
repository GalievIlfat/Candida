package com.example.candida.model.modelClass

import android.util.Log
import com.example.candida.model.AuthResult
import com.example.candida.model.data.project
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.candida.model.data.users
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.local.QueryEngine
import javax.security.auth.callback.Callback

class modelUsers {

    private val db = FirebaseFirestore.getInstance()
    private val collectionUser = db.collection("Users")
    val TAG = "001"

    fun addNewUser(user:users):AuthResult {
        var check = false


        collectionUser.add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                check = true
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }



        if (check) return AuthResult.Success
        else return AuthResult.Error
    }

    fun checkLoginExists(login:String):Boolean{
        var check= false
        collectionUser.whereEqualTo("login",login)
            .get()
            .addOnSuccessListener {
                check = true
            }
        return check
    }

    fun getUserByEmailAndPassword(login: String, password: String,callback: (users?)-> Unit) {
         db.collection("Users")
            .whereEqualTo("login", login)
             .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 1) {
                    for (document in documents) {
                        Log.d("users",document.id)
                        callback(document.toObject())
                    }
                }else {
                    callback(null)
                }
            }
    }
    fun getUserByLogin(login: String,callback: (users?)-> Unit) {
        db.collection("Users")
            .whereEqualTo("login", login)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 1) {
                    for (document in documents) {
                        callback(document.toObject())
                    }
                }else {
                    callback(null)
                }
            }
    }



}