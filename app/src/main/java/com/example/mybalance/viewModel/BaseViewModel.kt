package com.example.mybalance.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybalance.model.ActivityModel
import com.example.mybalance.model.DialogOneButtonModel
import com.example.mybalance.model.SnackBarModel

open class BaseViewModel : ViewModel() {

    var startActivity = MutableLiveData<ActivityModel>()
    var showSnackBar = MutableLiveData<SnackBarModel>()
    var showToast = MutableLiveData<Int>()
    var progress = MutableLiveData<Int>()
    var closeActivity = MutableLiveData<Unit>()
    var showOneButtonDialog = MutableLiveData<DialogOneButtonModel>()

    init {
        hideLoading()
    }

    fun showLoading() {
        progress.value = View.VISIBLE
    }

    fun hideLoading() {
        progress.value = View.GONE
    }

    fun closeActivity() {
        closeActivity.value = Unit
    }

    fun showActivity(activityModel: ActivityModel) {
        startActivity.value = activityModel
    }

    fun showSnackBar(snackBarModel: SnackBarModel) {
        showSnackBar.value = snackBarModel
    }

    fun showOneButtonDialog(dialogOneButtonModel: DialogOneButtonModel) {
        showOneButtonDialog.value = dialogOneButtonModel
    }

    fun showToast(text: Int) {
        showToast.value = text
    }

    fun onStartActivity() = startActivity
    fun onShowSnackBar() = showSnackBar
    fun onShowToast() = showToast
    fun onCloseActivity() = closeActivity
    fun onShowOneButtonDialog() = showOneButtonDialog

    companion object{
        const val TRANSACTION_COLLECTIONS_PATH = "transaction"
    }

}