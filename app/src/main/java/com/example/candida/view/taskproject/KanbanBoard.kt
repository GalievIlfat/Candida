package com.example.candida.view.taskproject

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.data.project
import com.example.candida.model.modelClass.modelProject
import com.example.candida.model.modelClass.modelTask
import com.example.candida.view.taskproject.recycle.ColumnAdapter
import com.example.candida.viewmodel.SharedViewModel
import com.example.candida.viewmodel.TaskProjectViewModel


class KanbanBoard : Fragment() {
    private lateinit var viewModelShare: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextNewColumn: EditText
    private lateinit var buttonAddColumn: Button
    private lateinit var projectId:String
    private  lateinit var buttonInformationProject:Button
    private lateinit var linearColumnAdd: LinearLayout
    private val viewModel: TaskProjectViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kanban_board, container, false)
        val email = arguments?.getString("emailParticipant")
        viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        init(view)


            // Log.d("emailParticipant",projectId.toString())
        viewModelShare.selectedItemProject.observe(viewLifecycleOwner) { selectedItem ->
            modelProject().getProjectID(selectedItem) {
                projectId = it
            }
            selectedItem.participants?.forEach(){
                if(it.mailParticipant == email){
                    viewModelShare.selectedItemRules(it.role.toString())
                }
            }
            getKonbanBoard(selectedItem)
        }

        buttonAddColumn.setOnClickListener{
            if(!editTextNewColumn.text.toString().isNullOrEmpty())
            {
                viewModel.addColumn(projectId,editTextNewColumn.text.toString())
            }
            else editTextNewColumn.error = "Заполните поле"
        }

        viewModelShare.selectedItemRule.observe(viewLifecycleOwner){
            if(it == "editor" || it == "viewer"){
                linearColumnAdd.visibility = View.GONE
            }
        }

        buttonInformationProject.setOnClickListener {
            Log.d("ProjectInfo",projectId)
            val bundle = Bundle()
            bundle.putString("key", projectId)
            val secondFragment = ProjectInfoFragment()
            secondFragment.arguments = bundle
            findNavController().navigate(R.id.action_kanbanBoard_to_projectInfoFragment,bundle)
        }

//        modelTask().hh()

        return view
    }
    fun init(item: View){
        recyclerView = item.findViewById(R.id.recycle_column)
        editTextNewColumn = item.findViewById(R.id.editTextTaskDescription)
        buttonAddColumn = item.findViewById(R.id.buttonAddColumn)
        buttonInformationProject = item.findViewById(R.id.buttonInformationProject)
        linearColumnAdd = item.findViewById(R.id.linearColumnAdd)
    }

    fun getKonbanBoard(project: project){
        viewModel.getKanbanBoard(project).observe(viewLifecycleOwner){
            viewModelShare.selectedItemRule.observe(viewLifecycleOwner){role->
                recyclerView.adapter = ColumnAdapter(it,viewModel,viewModelShare,role)
                recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            }

        }
    }



}