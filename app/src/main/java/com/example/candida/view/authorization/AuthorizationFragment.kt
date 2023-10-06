package com.example.candida.view.authorization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.candida.R
import com.example.candida.model.modelClass.modelProject
import com.example.candida.model.modelClass.modelTask
import com.example.candida.model.modelClass.modelUsers
import com.example.candida.view.taskproject.TaskProjectActivity
import com.example.candida.viewmodel.UserViewModel


class AuthorizationFragment : Fragment() {

    private lateinit var btnRegistration:Button
    private lateinit var buttonSignUp:Button
    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_authorization, container, false)
        init(view)

        buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        btnRegistration.setOnClickListener{
            userViewModel.getUser(editTextLogin.text.toString(),editTextPassword.text.toString()).observe(viewLifecycleOwner){
                if(it!=null){
                    val intent = Intent(this.context, TaskProjectActivity::class.java).apply {
                        putExtra("user",it.login)
                        putExtra("email",it.mail)
                    }
                    startActivity(intent)
                }
                else{
                    Toast.makeText(requireContext(),"Пароль или Логин введены неправильно",Toast.LENGTH_SHORT)
                }
            }

        }
        return view
    }

    private fun init(view: View) {

        btnRegistration = view.findViewById(R.id.btn)
        buttonSignUp = view.findViewById(R.id.buttonSignUp)
        editTextLogin = view.findViewById(R.id.editTextLogin)
        editTextPassword = view.findViewById(R.id.editTextTextPassword)
    }
}