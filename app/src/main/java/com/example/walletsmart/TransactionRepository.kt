package com.example.walletsmart

import android.content.Context
import com.example.walletsmart.model.TransactionModel

class TransactionRepository(context: Context) {
    private val transactionDataBase = TransactionDataBase.getDataBase(context).transactionDAO()

    fun insert(transaction: TransactionModel): Boolean {
        return transactionDataBase.insert(transaction) > 0
    }

//    fun update(guest: GuestModel): Boolean {
//        return guestDataBase.update(guest) > 0
//    }
//
//    fun delete(id: Int) {
//        val guest = get(id)
//        guestDataBase.delete(guest)
//    }
//
//    fun getAll(): List<GuestModel> {
//        return guestDataBase.getAll()
//    }
//
//    fun get(id: Int): GuestModel {
//        return guestDataBase.get(id)
//    }
//
//    fun getPresent(): List<GuestModel> {
//        return guestDataBase.getPresent()
//    }
//
//    fun getAbsent(): List<GuestModel> {
//        return guestDataBase.getAbsent()
//    }
}