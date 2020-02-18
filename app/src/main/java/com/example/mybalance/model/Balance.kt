package com.example.mybalance.model

import java.util.*

data class Balance(
    val title: String,
    val type: String,
    val amount: Long,
    val description: String,
    val date: Date
)