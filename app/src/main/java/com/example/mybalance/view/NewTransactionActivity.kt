package com.example.mybalance.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mybalance.R
import com.example.mybalance.databinding.ActivityNewTransactionBinding
import com.example.mybalance.model.TransactionType
import com.example.mybalance.viewModel.NewTransactionViewModel
import kotlinx.android.synthetic.main.activity_new_transaction.*


class NewTransactionActivity : BaseActivity() {
    private lateinit var activityMainBinding: ActivityNewTransactionBinding
    private val typeValues = arrayOf(
        TransactionType.Salary.name,
        TransactionType.Food.name,
        TransactionType.Clothes.name,
        TransactionType.Cleaning.name,
        TransactionType.Accessories.name,
        TransactionType.Travels.name,
        TransactionType.Services.name,
        TransactionType.Entertainment.name,
        TransactionType.Party.name,
        TransactionType.Other.name

    )

    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // If nothing is selected we don't have to do nothing.
        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            (viewModel as NewTransactionViewModel).onTypeSelected(typeValues[position])
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_new_transaction)
        viewModel = ViewModelProviders.of(this)[NewTransactionViewModel::class.java]

        subscribe()
    }

    override fun onResume() {
        super.onResume()
        activityMainBinding.viewmodel = viewModel as NewTransactionViewModel
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.executePendingBindings()


        type.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            typeValues
        )
        type.onItemSelectedListener = itemSelectedListener

    }
}
