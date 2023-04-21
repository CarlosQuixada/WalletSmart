package com.example.walletsmart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walletsmart.databinding.ActivityTransactionFormsBinding
import com.example.walletsmart.model.TransactionModel

class TransactionFormsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTransactionFormsBinding
    private lateinit var viewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionFormsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.radioReceita.isChecked = true

        observe()
    }

    override fun onClick(v: View) {
        if (v.id == binding.buttonSave.id) {
            var transaction = TransactionModel().apply {
                this.description = binding.editDescription.text.toString()
                this.value = binding.editValue.text.toString().toFloat()
                this.date = binding.editDate.text.toString()
                this.type = binding.radioReceita.isChecked
            }

            viewModel.save(transaction)

        }
    }

    private fun observe() {
        viewModel.saveGuest.observe(this, Observer {
            if (it != "Falha !") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }
        })
    }
}