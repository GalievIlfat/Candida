package com.example.candida.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.candida.model.AuthResult
import com.example.candida.model.data.project
import com.example.candida.model.data.users
import com.example.candida.model.modelClass.modelUsers
import java.security.MessageDigest

class UserViewModel: ViewModel() {
    private val modelUser = modelUsers()

    fun addUser(
        forename: String?,
        surname: String?,
        phoneNumber: String?,
        login: String?,
        mail: String?,
        password: String?
    ): AuthResult {

        if (forename.isNullOrEmpty()) {
            return AuthResult.isForename
        }
        if (surname.isNullOrEmpty()) {
            return AuthResult.isSurname
        }
        if (phoneNumber.isNullOrEmpty()) {
            return AuthResult.isphoneNumber
        }
        if (mail.isNullOrEmpty()) {
            return AuthResult.isMail
        }
        if (login.isNullOrEmpty()) {
            return AuthResult.isEmptyLogin
        }
        if (password.isNullOrEmpty()) {
            return AuthResult.isEmptyPassword
        }



        val user = users(
            forename = forename,
            surname = surname,
            phoneNumber = phoneNumber,
            login = login,
            mail = mail,
            password = password
        )

        if (modelUser.checkLoginExists(login)) {
            return AuthResult.LoginExist
        }

        return modelUser.addNewUser(user)
    }

    fun getUser(login: String, password: String): LiveData<users?> {
        val liveData = MutableLiveData<users?>()
        modelUser.getUserByEmailAndPassword(login = login,password = password){

                liveData.value = it

        }
        return liveData
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    sealed class Authorization(){
        object Success:Authorization()
        object Error:Authorization()
    }


}