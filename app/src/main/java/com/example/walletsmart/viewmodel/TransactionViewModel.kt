package com.example.walletsmart.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletsmart.model.TransactionModel
import com.example.walletsmart.repository.TransactionRepository

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionRepository = TransactionRepository(application)

    private val listAllTransaction = MutableLiveData<List<TransactionModel>>()
    val transactions: LiveData<List<TransactionModel>> = listAllTransaction

    private val transactionModel = MutableLiveData<TransactionModel>()
    val transaction: LiveData<TransactionModel> = transactionModel

    private val _saveTransaction = MutableLiveData<String>()
    val saveTransaction: LiveData<String> = _saveTransaction

    fun save(transaction: TransactionModel) {
        if (transaction.id == 0) {
            if (transactionRepository.insert(transaction)) {
                _saveTransaction.value = "Inserido Com Sucesso !"
            } else {
                _saveTransaction.value = "Falha"
            }
        } else {
            if (transactionRepository.update(transaction)) {
                _saveTransaction.value = "Atualizado Com Sucesso !"
            } else {
                _saveTransaction.value = "Falha !"
            }
        }

    }

    fun getAll() {
        listAllTransaction.value = transactionRepository.getAll()
    }

    fun get(id: Int) {
        transactionModel.value = transactionRepository.get(id)
    }

    fun getRevenue() {
        listAllTransaction.value = transactionRepository.getRevenue()
    }

    fun getExpenses() {
        listAllTransaction.value = transactionRepository.getExpenses()
    }

    fun delete(id: Int) {
        transactionRepository.delete(id)
    }

    fun calculateBalance(): Double {
        val listTransaction = transactionRepository.getAll()

        var balance = 0.00
        for (transaction in listTransaction) {

            if (transaction.type.toString().toBoolean()) {
                balance += transaction.value

            } else {
                balance -= transaction.value
            }
        }
        return balance
    }
}
