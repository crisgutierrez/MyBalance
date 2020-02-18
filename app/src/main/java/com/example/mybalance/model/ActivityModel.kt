package com.example.mybalance.model

import android.os.Bundle

data class ActivityModel(
    val activity: Class<*>,
    val bundle: Bundle? = null,
    val resultCode: Int = 0
)