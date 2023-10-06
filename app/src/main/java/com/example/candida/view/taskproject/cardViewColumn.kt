package com.example.candida.view.taskproject

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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.candida.R
import com.example.candida.model.checkTask
import com.example.candida.model.modelClass.modelTask
import com.example.candida.view.taskproject.recycle.checkListAdapter
import com.example.candida.viewmodel.SharedViewModel
import com.example.candida.viewmodel.TaskProjectViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class cardViewColumn : Fragment() {
    private lateinit var viewModelShare: SharedViewModel
    private val viewModel: TaskProjectViewModel by viewModels()

    private lateinit var editTextCardNameInformation: EditText
    private lateinit var textViewBeginDate: TextView
    private lateinit var textViewEndDate: TextView
    private lateinit var recycleViewCheckList:RecyclerView
    private lateinit var editTextAddTask:EditText
    private lateinit var imageViewAddTask:ImageView
    private lateinit var floatingActionButtonUpdate:FloatingActionButton
    private lateinit var linearTaskAdd:LinearLayout
    private lateinit var cardViewTeamsProject:CardView

    private var isChangeBeginDate = false
    private var isChangeEndDate = false
    private var isChangeCardName = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_view_column, container, false)
        val value = arguments?.getInt("position")
        val value1 = arguments?.getString("projectId")
        val value2 = arguments?.getString("ColumName")

        viewModelShare = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        init(view)

        textViewBeginDate.setOnClickListener {
            dateDialog(textViewBeginDate)
        }

        textViewEndDate.setOnClickListener {
            dateDialog(textViewEndDate)
        }

        imageViewAddTask.setOnClickListener {
            viewModelShare.selectedItemTask.observe(viewLifecycleOwner){
                if(value!=null){
                    viewModel.updateTask(it,value,editTextAddTask.text.toString())
                }
                checkList(value1!!,value2!!,value!!)

            }


        }

        updateCardName()
        updateBeginDate()
        updateEndDate()

        cardViewTeamsProject.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("position_card", value!!)
            bundle.putString("projectIdCard",value1)
            bundle.putString("ColumNameCard",value2)

            findNavController().navigate(R.id.action_cardViewColumn_to_addParticipantCardFragment,bundle)
        }


        if(value!= null && value1!=null && value2!=null) {
            viewModel.getInfoTask(value1, value2).observe(viewLifecycleOwner) { selectedItem ->
                editTextCardNameInformation.setText(selectedItem.cards[value].cardName.toString())
                if (selectedItem.cards[value].startDateCard != null) {
                    textViewBeginDate.setText(timpstampConvert(selectedItem.cards[value].startDateCard as Timestamp))
                }
                if (selectedItem.cards[value].endDateCard != null) {
                    textViewEndDate.setText(timpstampConvert(selectedItem.cards[value].endDateCard as Timestamp))
                }
//                if (!selectedItem.cards[value].checkList!!.isEmpty()) {
//                    recycleViewCheckList.adapter =
//                        checkListAdapter(selectedItem.cards[value].checkList as List<checkTask>)
//                    recycleViewCheckList.layoutManager = LinearLayoutManager(
//                        requireContext(),
//                        LinearLayoutManager.VERTICAL, false
//                    )
//                }
                checkList(value1,value2,value)
            }
        }
        floatingActionButtonUpdate.setOnClickListener {
            viewModelShare.selectedItemTask.observe(viewLifecycleOwner){
                if(isChangeBeginDate) {
                    viewModel.updateBeginDate(it,value!!,textViewBeginDate.text.toString())
                    isChangeBeginDate = false
                }
                if(isChangeEndDate) {
                    viewModel.updateEndDate(it,value!!,textViewEndDate.text.toString())
                    isChangeEndDate = false
                }
                if(isChangeCardName){
                    viewModel.updateCardName(it,value!!, editTextCardNameInformation.text.toString())
                    isChangeCardName = false
                }
            }

        }

        viewModelShare.selectedItemRule.observe(viewLifecycleOwner){
            if(it == "editor" || it == "viewer"){
                textViewBeginDate.isClickable = false
                textViewEndDate.isClickable = false

            }
            if(it == "editor"){
                floatingActionButtonUpdate.visibility = View.GONE
            }
            if(it == "viewer"){
                linearTaskAdd.visibility = View.GONE
            }
        }
        return view
    }

    fun checkList(value1:String,value2:String,value: Int){
        if(value!= null && value1!=null && value2!=null) {
            viewModelShare.selectedItemTask.observe(viewLifecycleOwner){
                viewModel.getCheckList(value1, value2,value).observe(viewLifecycleOwner){list->
                    recycleViewCheckList.adapter =
                        checkListAdapter(list,value,viewModel,it)
                    recycleViewCheckList.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL, false
                    )
                }
            }

        }
    }

    private fun changeInformation(value:Int) {
        viewModelShare.selectedItemTask.observe(viewLifecycleOwner) {
            if (isChangeBeginDate) {
                viewModel.updateBeginDate(it, value, textViewBeginDate.text.toString())
                isChangeBeginDate = false
            }
            if (isChangeEndDate) {
                viewModel.updateEndDate(it, value, textViewEndDate.text.toString())
                isChangeEndDate = false
            }
            if (isChangeCardName) {
                viewModel.updateCardName(it, value, editTextCardNameInformation.text.toString())
                isChangeCardName = false
            }
        }
    }

    private fun updateEndDate(){
        textViewEndDate.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isChangeEndDate = true
            }

        })
    }

    private fun updateBeginDate(){
        textViewBeginDate.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isChangeBeginDate = true
            }

        })
    }

    private fun updateCardName() {
        editTextCardNameInformation.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                isChangeCardName =  true
            }

        })
    }

    private fun timpstampConvert(dateCard:Timestamp):String{
        val date = dateCard.toDate()
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = date
        val formattedDate = dateFormatter.format(calendar.time)
        return formattedDate
    }


    private fun init(view: View) {
        editTextCardNameInformation = view.findViewById(R.id.editTextCardNameInformation)
        textViewBeginDate = view.findViewById(R.id.textViewBeginDateProject)
        textViewEndDate = view.findViewById(R.id.textViewEndDateProject)
        recycleViewCheckList = view.findViewById(R.id.recycleViewParticipant)
        editTextAddTask = view.findViewById(R.id.editTextAddTask)
        imageViewAddTask = view.findViewById(R.id.imageViewAddTask)
        floatingActionButtonUpdate = view.findViewById(R.id.floatingActionButtonUpdate)
        linearTaskAdd = view.findViewById(R.id.linearTaskAdd)
        cardViewTeamsProject = view.findViewById(R.id.cardViewTeamsProject)
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