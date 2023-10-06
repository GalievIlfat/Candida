package com.example.candida.model.data

import com.example.candida.model.checkTask
import com.google.firebase.Timestamp

data class card(
    var cardName: String? = "",
    var startDateCard: Timestamp? = null,
    var endDateCard: Timestamp? = null,
   // var participantsCard:List<String>? = emptyList(),
    var participantsCard:MutableList<String>? = mutableListOf(),
    val checkList:MutableList<checkTask>? = mutableListOf()
)
