package com.example.walletsmart.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsmart.databinding.RowAccountBinding
import com.example.walletsmart.model.AccountModel
import com.example.walletsmart.view.listener.OnAccountListener
import com.example.walletsmart.view.viewholder.AccountViewHolder

class AccountsAdapter : RecyclerView.Adapter<AccountViewHolder>() {
    private var accountList: List<AccountModel> = listOf()
    private lateinit var listener: OnAccountListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val item = RowAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accountList[position])
    }

    override fun getItemCount(): Int {
        return accountList.count()
    }

    fun updatedGuests(list: List<AccountModel>) {
        accountList = list
        notifyDataSetChanged()
    }

    fun attachListener(accountListener: OnAccountListener) {
        listener = accountListener
    }
}