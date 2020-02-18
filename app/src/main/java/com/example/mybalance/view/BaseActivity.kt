package com.example.mybalance.view

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer
import com.example.mybalance.model.ActivityModel
import com.example.mybalance.model.DialogOneButtonModel
import com.example.mybalance.model.SnackBarModel
import com.example.mybalance.viewModel.BaseViewModel

open class BaseActivity : AppCompatActivity() {

    lateinit var viewModel: BaseViewModel

    fun subscribe() {
        viewModel.onStartActivity().observe(this, Observer { startActivityModel(it) })
        viewModel.onShowSnackBar().observe(this, Observer { showSnackBar(it) })
        viewModel.onShowToast().observe(this, Observer { showToast(it) })
        viewModel.onCloseActivity().observe(this, Observer { closeActivity() })
        viewModel.onShowOneButtonDialog().observe(this, Observer { showOneButtonDialog(it) })
    }

    private fun startActivityModel(activityModel: ActivityModel) {
        activityModel.bundle?.let {
            if (activityModel.resultCode > 0) {
                startActivityForResult(Intent(this, activityModel.activity), activityModel.resultCode)
            } else {
                startActivity(Intent(this, activityModel.activity))
            }
        } ?: run {
            val intent = Intent(this, activityModel.activity)
            activityModel.bundle?.let { intent.putExtras(it) }
            if (activityModel.resultCode > 0) {
                startActivityForResult(intent, activityModel.resultCode)
            } else {
                startActivity(intent)
            }
        }
    }

    private fun showSnackBar(snackBarModel: SnackBarModel) {
        val snackbar = Snackbar
            .make(window.decorView.rootView, snackBarModel.text, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun showToast(text: Int) {
        Toast.makeText(this, getString(text), Toast.LENGTH_LONG).show()
    }

    private fun closeActivity() {
        finish()
    }

    private fun showOneButtonDialog(dialogOneButtonModel: DialogOneButtonModel) {
        AlertDialog.Builder(this)
            .setTitle(dialogOneButtonModel.title)
            .setMessage(dialogOneButtonModel.message)
            .setPositiveButton(
                dialogOneButtonModel.buttonText,
                dialogOneButtonModel.action
            ).show()
    }
}
