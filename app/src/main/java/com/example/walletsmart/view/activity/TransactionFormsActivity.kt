package com.example.walletsmart.view.activity

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walletsmart.constants.DataBaseConstants
import com.example.walletsmart.databinding.ActivityTransactionFormsBinding
import com.example.walletsmart.model.AccountModel
import com.example.walletsmart.model.TransactionModel
import com.example.walletsmart.view.adapter.AccountsAdapter
import com.example.walletsmart.viewmodel.AccountViewModel
import com.example.walletsmart.viewmodel.TransactionViewModel
import java.util.Calendar

class TransactionFormsActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityTransactionFormsBinding

    private val accountAdapter = AccountsAdapter()

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var accountViewModel: AccountViewModel

    private var transactionId = 0
    private var accountSelectedId = 0
    private lateinit var listAccounts: List<AccountModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionFormsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        binding.buttonSave.setOnClickListener(this)
        binding.textDateValue.setOnClickListener(this)
        binding.radioReceita.isChecked = true

        binding.spinnerAccount.onItemSelectedListener = this

        observe()
        loadData()
        loadSpinner()
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
                    this.accountId = accountSelectedId
                }

                transactionViewModel.save(transaction)
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

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        accountSelectedId = listAccounts[position].id
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        Toast.makeText(this, "Nada", Toast.LENGTH_LONG).show()
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
            transactionViewModel.get(transactionId)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textDateValue.windowToken, 0)
    }

    private fun observe() {
        transactionViewModel.transaction.observe(this, Observer {
            binding.editDescription.setText(it.description)
            binding.editValue.setText(it.value.toString())
            binding.textDateValue.setText(it.date)

            val accountPosition =
                listAccounts.indexOfFirst { listIt -> listIt.id == it.id }
            binding.spinnerAccount.setSelection(accountPosition)


            if (it.type) {
                binding.radioReceita.isChecked = true

            } else {
                binding.radioDespesa.isChecked = true
            }
        })

        transactionViewModel.saveTransaction.observe(this, Observer {
            if (it != "Falha !") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }
        })

        accountViewModel.accounts.observe(this, Observer {
            accountAdapter.updatedGuests(it)
        })
    }

    private fun loadSpinner() {
        accountViewModel.getAll()
        listAccounts = accountViewModel.accounts.value!!

        val listAccountName = listAccounts.map { it.name }

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listAccountName
        )

        binding.spinnerAccount.adapter = spinnerAdapter
    }

}

