package com.example.mybalance.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import com.example.mybalance.R
import com.example.mybalance.model.Balance
import com.example.mybalance.model.DialogOneButtonModel
import com.example.mybalance.model.TransactionType
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class NewTransactionViewModel : BaseViewModel() {
    var title = ObservableField<String>()
    var amount = ObservableField<String>()
    var description = ObservableField<String>()
    var type: String? = null

    /**
     * Set the transaction type
     */
    fun onTypeSelected(type: String) {
        this.type = type
    }

    /**
     * Add the new transaction to Firebase
     */
    fun onClickCreate() {
        showLoading()

        FirebaseFirestore.getInstance().collection(TRANSACTION_COLLECTIONS_PATH)
            .add(Balance(
                title = title.get() ?: "",
                type = type ?: "",
                amount = getAmountSpent() ?: 0,
                description = description.get() ?: "",
                date = Date()
            ))
            .addOnSuccessListener {
                hideLoading()
                showOneButtonDialog(DialogOneButtonModel(
                    R.string.transaction_dialog_successfully_title,
                    R.string.transaction_dialog_successfully_message,
                    R.string.transaction_dialog_ok_button
                ) { _, _ -> closeActivity()})
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                hideLoading()
                showOneButtonDialog(DialogOneButtonModel(
                    R.string.transaction_dialog_failure_title,
                    R.string.transaction_dialog_failure_message,
                    R.string.transaction_dialog_ok_button
                ) { dialog, _ -> dialog.dismiss()})
            }
    }

    /**
     * Return the amount spent with the proper sign
     * If the type is not salary it means it is a spending hence it should be negative
     */
    private fun getAmountSpent() : Long? {
        var amountSpent = amount.get()?.toLong()

        if (type != TransactionType.Salary.name) {
            amountSpent = amountSpent?.unaryMinus()
        }

        return amountSpent
    }

    companion object{
        const val TAG = "NewTransactionViewModel"
    }

}