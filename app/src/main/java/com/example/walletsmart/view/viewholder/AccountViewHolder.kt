package com.example.walletsmart.view.viewholder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsmart.databinding.RowAccountBinding
import com.example.walletsmart.model.AccountModel
import com.example.walletsmart.view.listener.OnAccountListener

class AccountViewHolder(
    private val bind: RowAccountBinding,
    private val listener: OnAccountListener
) : RecyclerView.ViewHolder(bind.root) {

    fun bind(account: AccountModel) {
        bind.textName.text = account.name

        bind.textName.setOnClickListener {
            listener.onClick(account.id)
        }

        bind.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Remoção de Conta")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton("Sim") { dialog, which -> listener.onDelete(account.id) }
                .setNegativeButton("Não", null)
                .create()
                .show()

            true
        }
    }
}