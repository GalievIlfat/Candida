package com.example.candida.view.taskproject

import android.app.ActionBar
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.modelClass.modelProject
import com.example.candida.view.taskproject.recycle.ProjectAdapter
import com.example.candida.view.taskproject.recycle.participantAdapter
import com.example.candida.viewmodel.ProjectViewModel
import com.example.candida.viewmodel.SharedViewModel
import com.example.candida.viewmodel.TaskProjectViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class ProjectInfoFragment : Fragment() {

    private val viewModel: ProjectViewModel by viewModels()
    private lateinit var viewModelShare: SharedViewModel
    private lateinit var editTextProjectInformation:EditText
    private lateinit var textViewBeginDateProject:TextView
    private lateinit var textViewEndDateProject:TextView
    private lateinit var floatingActionButtonProject:FloatingActionButton
    private lateinit var cardViewTeamsProject:CardView
    private lateinit var recycleViewParticipant:RecyclerView
    private var isChangeBeginDate = false
    private var isChangeEndDate = false
    private var isChangeProjectName = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_project_info, container, false)
        init(view)
        viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        val value = arguments?.getString("key")
        val bundle = Bundle()
        bundle.putString("projectId", value)
        val secondFragment = ProjectInfoFragment()
        secondFragment.arguments = bundle
        //Log.d("ProjectInfo",value.toString())
        if(value!=null){
            viewModel.getProjectInfo(value).observe(viewLifecycleOwner){
                editTextProjectInformation.setText(it.projectName)
                if(it.startDate!=null){
                    textViewBeginDateProject.setText(viewModel.timestampConvert(it.startDate))
                }
                if(it.endDate!=null){
                    textViewEndDateProject.setText(viewModel.timestampConvert(it.endDate))
                }
                recycleViewParticipant.layoutManager = LinearLayoutManager(this.context,
                    LinearLayoutManager.VERTICAL,false)
                recycleViewParticipant.adapter = participantAdapter(it.participants!!)
            }

        }
        viewModelShare.selectedItemRule.observe(viewLifecycleOwner){
            if(it == "editor" || it == "viewer"){
                textViewBeginDateProject.isClickable = false
                textViewEndDateProject.isClickable = false
                floatingActionButtonProject.visibility = View.GONE
                cardViewTeamsProject.isClickable = false
            }
        }


        textViewBeginDateProject.setOnClickListener {
            dateDialog(textViewBeginDateProject)
        }
        textViewEndDateProject.setOnClickListener {
            dateDialog(textViewEndDateProject)
        }

        floatingActionButtonProject.setOnClickListener {
            if(value!=null){
                changeInformation(value)

                viewModel.getProjectInfo(value).observe(viewLifecycleOwner){
                    viewModelShare.selectItem(it)
                }
            }

        }

        cardViewTeamsProject.setOnClickListener {
            findNavController().navigate(R.id.action_projectInfoFragment_to_addParticipantFragment,bundle)
        }


        changetextViewBeginDateProject()
        changetextViewEndDateProject()
        changeeditTextProjectInformation()


        return view
    }

    private fun changeeditTextProjectInformation() {
        editTextProjectInformation.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isChangeProjectName = true
            }

        })
    }

    private fun changetextViewBeginDateProject() {
        textViewBeginDateProject.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isChangeBeginDate = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun changetextViewEndDateProject() {
        textViewEndDateProject.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                isChangeEndDate = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun changeInformation(value:String) {
        if(isChangeBeginDate) {
            viewModel.updateBeginDate(textViewBeginDateProject.text.toString(),value)
           // findNavController().navigate(R.id.action_projectInfoFragment_to_projectFragment)
            isChangeBeginDate = false
        }
        if(isChangeEndDate) {
            viewModel.updateEndDate(textViewEndDateProject.text.toString(),value)
            isChangeEndDate = false
            //findNavController().navigate(R.id.action_projectInfoFragment_to_projectFragment)
        }
        if(isChangeProjectName){
            viewModel.updateProjectName(editTextProjectInformation.text.toString(),value)
            isChangeProjectName = false
           // findNavController().navigate(R.id.action_projectInfoFragment_to_projectFragment)
        }
    }

    fun init(view: View){
        editTextProjectInformation = view.findViewById(R.id.editTextProjectInformation)
        textViewBeginDateProject = view.findViewById(R.id.textViewBeginDateProject)
        textViewEndDateProject = view.findViewById(R.id.textViewEndDateProject)
        floatingActionButtonProject = view.findViewById(R.id.floatingActionButtonProject)
        cardViewTeamsProject = view.findViewById(R.id.cardViewTeamsProject)
        recycleViewParticipant = view.findViewById(R.id.recycleViewParticipant)
    }



    fun dateDialog(textView: TextView){
        // Получаем текущую дату и время
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Создаем DatePickerDialog для выбора даты
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.MyDialogTheme,
            { _, year, monthOfYear, dayOfMonth ->
                // Создаем TimePickerDialog для выбора времени
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    R.style.MyDialogTheme,
                    { _, hourOfDay, minute ->
                        // Объединяем выбранную дату и время
                        val selectedDateTime = Calendar.getInstance()
                        selectedDateTime.set(year, monthOfYear, dayOfMonth, hourOfDay, minute)

                        // Преобразуем выбранную дату и время в формат строкового значения
                        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                        val selectedDateTimeString = dateFormat.format(selectedDateTime.time)

                        // Устанавливаем выбранную дату и время в TextView
                        textView.text = selectedDateTimeString + ":00"
                    },
                    currentHour,
                    currentMinute,
                    true
                )
                // Отображаем диалоговое окно выбора времени
                timePickerDialog.show()
            },
            currentYear,
            currentMonth,
            currentDay
        )
        // Отображаем диалоговое окно выбора даты
        datePickerDialog.show()
    }

}