package com.example.walletsmart.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsmart.view.listener.OnTransactionListener
import com.example.walletsmart.view.viewholder.TransactionViewHolder
import com.example.walletsmart.databinding.RowTransactionBinding
import com.example.walletsmart.model.TransactionModel

class TransactionsAdapter : RecyclerView.Adapter<TransactionViewHolder>() {
    private var transactionList: List<TransactionModel> = listOf()
    private lateinit var listener: OnTransactionListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val item = RowTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(item, listener)
    }

    override fun getItemCount(): Int {
        return transactionList.count()
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    fun updatedGuests(list: List<TransactionModel>) {
        transactionList = list
        notifyDataSetChanged()
    }

    fun attachListener(transactionListener: OnTransactionListener){
        listener = transactionListener
    }
}