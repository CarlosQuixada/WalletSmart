package com.example.walletsmart.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletsmart.model.AccountModel
import com.example.walletsmart.repository.AccountRepository

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val accountRepository = AccountRepository(application)

    private val listAccounts = MutableLiveData<List<AccountModel>>()
    val accounts: LiveData<List<AccountModel>> = listAccounts

    private val accountModel = MutableLiveData<AccountModel>()
    val account: LiveData<AccountModel> = accountModel

    private val _saveAccount = MutableLiveData<String>()
    val saveAccount: LiveData<String> = _saveAccount

    fun save(account: AccountModel) {
        if (account.id == 0) {
            if (accountRepository.insert(account)) {
                _saveAccount.value = "Inserido Com Sucesso !"
            } else {
                _saveAccount.value = "Falha"
            }
        } else {
            if (accountRepository.update(account)) {
                _saveAccount.value = "Atualizado Com Sucesso !"
            } else {
                _saveAccount.value = "Falha !"
            }
        }
    }

    fun getAll() {
        listAccounts.value = accountRepository.getAll()
    }

    fun get(id: Int) {
        accountModel.value = accountRepository.get(id)
    }

    fun delete(id: Int) {
        accountRepository.delete(id)
    }
}