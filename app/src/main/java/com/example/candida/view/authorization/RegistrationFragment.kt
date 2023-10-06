package com.example.candida.view.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.candida.R
import com.example.candida.model.AuthResult
import com.example.candida.viewmodel.ProjectViewModel
import com.example.candida.viewmodel.UserViewModel


class RegistrationFragment : Fragment() {

    private lateinit var editTextForename:EditText
    private lateinit var editTextSurname:EditText
    private lateinit var editTextEmailAddress:EditText
    private lateinit var editTextLoginRegistration:EditText
    private lateinit var editTextPasswordRegistration:EditText
    private lateinit var editTextPhone:EditText
    private lateinit var buttonSignUpRegistration:Button

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        init(view)
        // Inflate the layout for this fragment

        buttonSignUpRegistration.setOnClickListener {
            onSignUpButtonClicked()
        }
        return view
    }

    fun onSignUpButtonClicked() {
        val forename = editTextForename.text.toString()
        val surname = editTextSurname.text.toString()
        val phoneNumber = editTextPhone.text.toString()
        val login = editTextLoginRegistration.text.toString()
        val mail = editTextEmailAddress.text.toString()
        val password = editTextPasswordRegistration.text.toString()

        val result = userViewModel.addUser(forename, surname, phoneNumber, login, mail, password)
        when (result) {
            AuthResult.isForename -> {
                editTextForename.error = "Поле пустое"
            }
            AuthResult.isSurname -> {
                editTextSurname.error = "Поле пустое"
            }

            AuthResult.isphoneNumber -> {
                editTextPhone.error = "Поле пустое"
            }
            AuthResult.isMail -> {
                editTextEmailAddress.error = "Поле пустое"
            }
            AuthResult.isEmptyLogin -> {
                editTextLoginRegistration.error = "Поле пустое"
            }
            AuthResult.isEmptyPassword -> {
                editTextPasswordRegistration.error = "Поле пустое"
            }

            AuthResult.LoginExist -> {
                editTextLoginRegistration.error = "Данный логин ужее существует"
            }

            AuthResult.Error -> {
                Toast.makeText(requireContext(),"Произошла ошибка",Toast.LENGTH_LONG)
            }

            is AuthResult.Success -> {
                Toast.makeText(requireContext(),"Вы успешно заргестрировались",Toast.LENGTH_LONG)
            }

        }
    }

    private fun init(view: View) {
        editTextForename = view.findViewById(R.id.editTextForename)
        editTextSurname = view.findViewById(R.id.editTextSurname)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        editTextEmailAddress = view.findViewById(R.id.editTextEmailAddress)
        editTextLoginRegistration = view.findViewById(R.id.editTextLoginRegistration)
        editTextPasswordRegistration = view.findViewById(R.id.editTextPasswordRegistration)
        buttonSignUpRegistration = view.findViewById(R.id.buttonSignUpRegistration)

    }


}