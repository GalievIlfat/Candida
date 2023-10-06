package com.example.candida.model

sealed class AuthResult {
    object LoginExist:AuthResult()
    object Success:AuthResult()
    object Error:AuthResult()

    object isEmptyLogin:AuthResult()
    object isEmptyPassword:AuthResult()
    object isphoneNumber:AuthResult()
    object isForename:AuthResult()
    object isSurname:AuthResult()
    object isMail:AuthResult()
}