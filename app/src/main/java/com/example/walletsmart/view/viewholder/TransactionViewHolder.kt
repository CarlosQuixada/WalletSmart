package com.example.walletsmart.view.viewholder;

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView;

import com.example.walletsmart.databinding.RowTransactionBinding;
import com.example.walletsmart.model.TransactionModel
import com.example.walletsmart.view.listener.OnTransactionListener

class TransactionViewHolder(
    private val bind: RowTransactionBinding,
    private val listener: OnTransactionListener
) : RecyclerView.ViewHolder(bind.root) {

    fun bind(transaction: TransactionModel) {
        bind.textName.text = transaction.description

        bind.textName.setOnClickListener {
            listener.onClick(transaction.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de Transação")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("Sim") { dialog, which -> listener.onDelete(transaction.id) }
                .setNegativeButton("Não", null)
                .create()
                .show()

            true
        }
    }
}
