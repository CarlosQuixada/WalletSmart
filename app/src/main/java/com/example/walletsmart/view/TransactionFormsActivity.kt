package com.example.walletsmart.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walletsmart.constants.DataBaseConstants
import com.example.walletsmart.databinding.ActivityTransactionFormsBinding
import com.example.walletsmart.model.TransactionModel
import com.example.walletsmart.viewmodel.TransactionViewModel
import java.util.Calendar

class TransactionFormsActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityTransactionFormsBinding
    private lateinit var viewModel: TransactionViewModel
    private var transactionId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionFormsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.textDateValue.setOnClickListener(this)

        binding.radioReceita.isChecked = true

        observe()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == binding.buttonSave.id) {
            val validate = validate_forms()

            if (validate) {
                val transaction = TransactionModel().apply {
                    this.id = transactionId
                    this.description = binding.editDescription.text.toString()
                    this.value = binding.editValue.text.toString().toFloat()
                    this.date = binding.textDateValue.text.toString()
                    this.type = binding.radioReceita.isChecked
                }

                viewModel.save(transaction)
            }

        } else if (v.id == binding.textDateValue.id) {
            hideKeyboard()

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        binding.textDateValue.setText("$day/${month + 1}/$year")
    }

    private fun validate_forms(): Boolean {
        if (binding.editDescription.text.isNullOrEmpty()) {
            binding.editDescription.error = "Descrição está vazia!"

            return false

        } else if (binding.editValue.text.isNullOrEmpty()) {
            binding.editValue.error = "Valor está vazio!"
            return false

        } else if (binding.textDateValue.text.trim().toString() == "DD/MM/YYYY") {
            Toast.makeText(this, "Campo de Data está vazio!", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun loadData() {
        val bundle = intent.extras

        if (bundle != null) {
            transactionId = bundle.getInt(DataBaseConstants.TRANSACTION.ID_NAME)
            viewModel.get(transactionId)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textDateValue.windowToken, 0)
    }

    private fun observe() {
        viewModel.transaction.observe(this, Observer {
            binding.editDescription.setText(it.description)
            binding.editValue.setText(it.value.toString())
            binding.textDateValue.setText(it.date)

            if (it.type) {
                binding.radioReceita.isChecked = true

            } else {
                binding.radioDespesa.isChecked = true
            }
        })

        viewModel.saveTransaction.observe(this, Observer {
            if (it != "Falha !") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }
        })
    }
}

