package com.example.candida.view.taskproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.candida.R
import com.example.candida.viewmodel.ProjectViewModel
import com.example.candida.viewmodel.SharedViewModel


class addParticipantFragment : Fragment() {

    private lateinit var editTextDropdown:EditText
    private lateinit var viewModelShare: SharedViewModel
    private lateinit var spinner: Spinner
    private lateinit var editTextParticipant:EditText
    private lateinit var buttonInvite: Button

    private var changeTextRule = ""

    private val viewModel: ProjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_participant, container, false)
        init(view)
        val value = arguments?.getString("projectId")

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.role_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                changeTextRule =  parent?.getItemAtPosition(position).toString()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // здесь можно выполнить какой-либо код, если ничего не выбрано
            }
        }

        buttonInvite.setOnClickListener {
            if(value!=null){
                viewModel.updateParticipant(editTextParticipant.text.toString(),changeTextRule,value)
                viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
                viewModel.getProjectInfo(value).observe(viewLifecycleOwner){
                    viewModelShare.selectItem(it)
                }
            }

        }
        return view
    }

    private fun init(view: View) {
       // editTextDropdown = view.findViewById(R.id.editTextDropdown)
        editTextParticipant = view.findViewById(R.id.editTextParticipant)
        spinner =  view.findViewById(R.id.spinner)
        buttonInvite = view.findViewById(R.id.buttonInvite)
    }


}