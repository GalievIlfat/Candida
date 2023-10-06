package com.example.candida.view.taskproject

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.modelClass.modelProject
import com.example.candida.model.modelClass.modelTask
import com.example.candida.view.taskproject.recycle.ColumnAdapter
import com.example.candida.view.taskproject.recycle.ProjectAdapter
import com.example.candida.viewmodel.ProjectViewModel
import com.example.candida.view.taskproject.interfaces.OnItemClickListener
import com.example.candida.viewmodel.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProjectFragment : Fragment(){
    private lateinit var viewModelShare: SharedViewModel
    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var  actionButton: FloatingActionButton
    private var email:String =""

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)
        init(view)
        getEmail()

        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Устанавливаем обработчик нажатия на кнопку "назад"
        setHasOptionsMenu(true)
        recyclerView.setOnCreateContextMenuListener(this)
        actionButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("email", email)
            Log.d("Fufu",email+ " fufu")
            findNavController().navigate(R.id.action_projectFragment_to_createProjectFragment,bundle)
        }


        loadProject()
        return view
    }

    fun loadProject(){

        viewModel.getDataFromFirestore(mail = email).observe(viewLifecycleOwner){
            recyclerView.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
            recyclerView.adapter = ProjectAdapter(it,object:OnItemClickListener{
                override fun onItemClick(view: View, position: Int) {
                    val bundle = Bundle()
                    bundle.putString("emailParticipant", email)
                    viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
                    val selectedItem = it[position]
                    viewModelShare.selectItem(selectedItem)
                    findNavController().navigate(R.id.action_projectFragment_to_kanbanBoard,bundle)
                }
            })

        }
    }

    fun init(view:View){
        recyclerView = view.findViewById(R.id.projectNameList)
        actionButton = view.findViewById(R.id.ActionButtonAddProject)

    }

    fun getEmail(){
        viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModelShare.selectedItemUser.observe(viewLifecycleOwner, { selectedItem ->
            email = selectedItem.mail.toString()
            loadProject()
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_projectFragment_to_userProfileFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onPause() {
        getEmail()
        super.onPause()
    }
}