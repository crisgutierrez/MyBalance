package com.example.mybalance.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybalance.R
import com.example.mybalance.adapter.TransactionAdapter
import com.example.mybalance.databinding.ActivityMainBinding
import com.example.mybalance.model.Balance
import com.example.mybalance.viewModel.MyBalanceViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val adapter = TransactionAdapter()
    private lateinit var activityMainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[MyBalanceViewModel::class.java]
        linearLayoutManager = LinearLayoutManager(this)

        subscribe()
    }

    override fun onResume() {
        super.onResume()
        (viewModel as MyBalanceViewModel).init()

        activityMainBinding.viewmodel = viewModel as MyBalanceViewModel
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.executePendingBindings()

        initRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView() {
        transaction_recycler_view.layoutManager = linearLayoutManager
        transaction_recycler_view.adapter = adapter
        (viewModel as MyBalanceViewModel).onTransactionList().observe(this, androidx.lifecycle.Observer { getTransactionList(it) })

        val dividerItemDecoration = DividerItemDecoration(
            transaction_recycler_view.context,
            LinearLayout.HORIZONTAL
        )
        transaction_recycler_view.addItemDecoration(dividerItemDecoration)
    }


    private fun getTransactionList(list: ArrayList<Balance>) {
        adapter.setData(list)
    }
}
