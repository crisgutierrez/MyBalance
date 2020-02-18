package com.example.mybalance.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mybalance.model.ActivityModel
import com.example.mybalance.model.Balance
import com.example.mybalance.view.NewTransactionActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class MyBalanceViewModel : BaseViewModel() {

    private val dataBase = FirebaseFirestore.getInstance()

    var transactionList = MutableLiveData<ArrayList<Balance>>()

    fun init() {
        getAllTransaction()
    }

    /**
     * Get the latest 30 transaction from Firebase.
     */
    private fun getAllTransaction() {
        showLoading()
        dataBase.collection(TRANSACTION_COLLECTIONS_PATH)
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(TRANSACTION_AMOUNT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val balanceList: ArrayList<Balance> = arrayListOf()
                    for (document in task.result!!) {
                        hideLoading()
                        val balance = Balance(
                            document.data["title"].toString(),
                            document.data["type"].toString(),
                            document.data["amount"] as Long,
                            document.data["description"].toString(),
                            document.data["date"] as Date
                        )
                        balanceList.add(balance)
                        Log.d(TAG, document.id + " => " + balance)
                    }
                    transactionList.value = balanceList
                } else {
                    hideLoading()
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

    fun onTransactionList() = transactionList

    /**
     * Open New Transaction activity.
     */
    fun onClickNewTransaction() {
        showActivity(ActivityModel(NewTransactionActivity::class.java))
    }

    companion object{
        const val TAG = "MyBalanceViewModel"
        const val TRANSACTION_AMOUNT = 30L
    }
}