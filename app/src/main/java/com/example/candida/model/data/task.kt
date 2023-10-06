package com.example.candida.model

import com.example.candida.model.data.card
import com.google.firebase.Timestamp


data class task(
    val projectID:String? = "",
    val nameColumn:String? = "",
    val cards:List<card> = emptyList()
)

data class checkTask (
    var check:Boolean? = null,
    val task:String? = ""
)
