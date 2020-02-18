package com.example.mybalance.model

import android.content.DialogInterface

class DialogOneButtonModel(
    val title: Int,
    val message: Int,
    val buttonText: Int,
    val action: (dialog: DialogInterface, which: Int) -> Unit
)



