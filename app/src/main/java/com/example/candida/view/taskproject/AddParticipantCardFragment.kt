package com.example.candida.view.taskproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.view.taskproject.recycle.checkListAdapter
import com.example.candida.view.taskproject.recycle.participanCardsAdapter
import com.example.candida.view.taskproject.recycle.participantAdapter
import com.example.candida.viewmodel.SharedViewModel
import com.example.candida.viewmodel.TaskProjectViewModel


class AddParticipantCardFragment : Fragment() {

    private lateinit var buttonAddParticipantCard: Button
    private lateinit var spinnerParticipant: Spinner
    private lateinit var recycleViewParticipanCard:RecyclerView
    private lateinit var viewModelShare: SharedViewModel
    private val viewModel: TaskProjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_participant_card, container, false)
        viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        val value = arguments?.getInt("position_card")
        val value1 = arguments?.getString("projectIdCard")
        val value2 = arguments?.getString("ColumNameCard")


        init(view)
        Log.d("Tagg",value.toString())
        var mailParticipant = ""

        viewModelShare.selectedItemProject.observe(viewLifecycleOwner){
            val values = it.participants?.map { "${it.mailParticipant}" }

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, values as List<String>)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerParticipant.adapter = adapter
        }
        spinnerParticipant.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                mailParticipant = selectedItem
                Log.d("Tagg",selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // код, который должен выполниться, когда ничего не выбрано
            }
        }
        buttonAddParticipantCard.setOnClickListener {
            viewModelShare.selectedItemTask.observe(viewLifecycleOwner){task->
                if(value!=null){
                    viewModel.updateParticipant(task,value,mailParticipant)
                }
            }
            if(value1!=null && value!= null && value2!=null) participantCards(value1,value2,value)


        }
        viewModelShare.selectedItemRule.observe(viewLifecycleOwner){
            if(it == "editor" || it == "viewer"){
                buttonAddParticipantCard.visibility = View.GONE
                spinnerParticipant.visibility = View.GONE
            }
        }
        if(value1!=null && value!= null && value2!=null) participantCards(value1,value2,value)

        return view
    }
    fun participantCards(value1:String,value2:String,value: Int){
        if(value!= null && value1!=null && value2!=null) {
            viewModelShare.selectedItemTask.observe(viewLifecycleOwner){
                viewModel.getParticipantsCard(value1, value2,value).observe(viewLifecycleOwner){list->
                    recycleViewParticipanCard.adapter =
                        participanCardsAdapter(list)
                    recycleViewParticipanCard.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL, false
                    )
                }
            }

        }
    }

    private fun init(view: View) {
        buttonAddParticipantCard = view.findViewById(R.id.buttonAddParticipantCard)
        spinnerParticipant = view.findViewById(R.id.spinnerParticipant)
        recycleViewParticipanCard = view.findViewById(R.id.recycleViewParticipanCard)
    }

}