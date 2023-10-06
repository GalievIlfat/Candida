package com.example.candida.view.taskproject

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.candida.R
import com.example.candida.viewmodel.ProjectViewModel


class CreateProjectFragment : Fragment() {
    private  lateinit var  editTextProjectName: EditText
    private  lateinit var  buttonAddNewProject: Button

    private val projectViewmodel: ProjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_project, container, false)
        setHasOptionsMenu(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        init(view)

        buttonAddNewProject.setOnClickListener {
            addProject()
            findNavController().navigate(R.id.action_createProjectFragment_to_projectFragment)
        }

        return view
    }

    private fun addProject(){
        if(!TextUtils.isEmpty(editTextProjectName.getText().toString().trim())){
            val args = arguments
            val myValue = args?.getString("email")
            Log.d("Create",myValue!!)
            projectViewmodel.addDataProject(editTextProjectName.text.toString(),myValue)

        }
        else{
            editTextProjectName.error = "Поле не заполнено"
        }
    }


    fun init(view:View){
        editTextProjectName = view.findViewById(R.id.editTextProjectName)
        buttonAddNewProject = view.findViewById(R.id.buttonAddNewProject)
    }
}