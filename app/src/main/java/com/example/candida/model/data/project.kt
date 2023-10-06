package com.example.candida.model.data

import com.google.firebase.Timestamp


data class project(
    val projectName:String = "",
    val participants:List<participant>?= emptyList(),
    val startDate: Timestamp?=null,
    val endDate:Timestamp?=null
)
