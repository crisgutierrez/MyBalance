package com.example.mybalance.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybalance.R
import com.example.mybalance.inflate
import com.example.mybalance.model.Balance
import kotlinx.android.synthetic.main.transaction_item.view.*
import java.text.SimpleDateFormat

class TransactionAdapter :
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>()  {
    private var transactionList = ArrayList<Balance>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHolder {
        val inflatedView = parent.inflate(R.layout.transaction_item, false)
        return TransactionHolder(inflatedView)

    }

    override fun getItemCount() = transactionList.size

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transactionItem = transactionList[position]
        holder.bindBalance(transactionItem)

    }

    fun setData(list: ArrayList<Balance>) {
        transactionList = list
        notifyDataSetChanged()
    }



    class TransactionHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private lateinit var balance: Balance

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            // TODO open transaction details
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindBalance(balance: Balance) {
            this.balance = balance
            val date = SimpleDateFormat("dd-MM-yy").format(balance.date)
            view.transaction_date.text = date.toString()
            view.transaction_title.text = balance.title
            view.transaction_amount.text = balance.amount.toString()
        }
    }

}
