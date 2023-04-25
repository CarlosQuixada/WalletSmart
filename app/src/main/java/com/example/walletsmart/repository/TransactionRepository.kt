package com.example.walletsmart.repository

import android.content.Context
import com.example.walletsmart.model.TransactionModel

class TransactionRepository(context: Context) {
    private val transactionDataBase = WalletDataBase.getDataBase(context).transactionDAO()

    fun insert(transaction: TransactionModel): Boolean {
        return transactionDataBase.insert(transaction) > 0
    }

    fun update(transaction: TransactionModel): Boolean {
        return transactionDataBase.update(transaction) > 0
    }

    fun delete(id: Int) {
        val guest = get(id)
        transactionDataBase.delete(guest)
    }

    fun getAll(): List<TransactionModel> {
        return transactionDataBase.getAll()
    }

    fun get(id: Int): TransactionModel {
        return transactionDataBase.get(id)
    }

    fun getRevenue(): List<TransactionModel> {
        return transactionDataBase.getRevenue()
    }

    fun getExpenses(): List<TransactionModel> {
        return transactionDataBase.getExpenses()
    }
}