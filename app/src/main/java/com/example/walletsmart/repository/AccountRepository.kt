package com.example.walletsmart.repository

import android.content.Context
import com.example.walletsmart.model.AccountModel

class AccountRepository(context: Context) {
    private val accountDataBase = WalletDataBase.getDataBase(context).accountDAO()

    fun insert(account: AccountModel): Boolean {
        return accountDataBase.insert(account) > 0
    }

    fun update(account: AccountModel): Boolean {
        return accountDataBase.update(account) > 0
    }

    fun delete(id: Int) {
        val account = get(id)
        accountDataBase.delete(account)
    }

    fun getAll(): List<AccountModel> {
        return accountDataBase.getAll()
    }

    fun get(id: Int): AccountModel {
        return accountDataBase.get(id)
    }
}