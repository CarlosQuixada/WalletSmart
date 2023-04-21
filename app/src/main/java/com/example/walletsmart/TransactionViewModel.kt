package com.example.walletsmart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletsmart.model.TransactionModel

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository(application)

    private val _saveGuest = MutableLiveData<String>()
    val saveGuest: LiveData<String> = _saveGuest

    fun save(transaction: TransactionModel) {
        if (transactionRepository.insert(transaction)) {
            _saveGuest.value = "Inserido Com Sucesso !"
        } else {
            _saveGuest.value = "Falha !"
        }

    }

}
