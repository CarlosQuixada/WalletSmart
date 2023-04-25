package com.example.walletsmart.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walletsmart.constants.DataBaseConstants
import com.example.walletsmart.databinding.ActivityAccountFormsBinding
import com.example.walletsmart.model.AccountModel
import com.example.walletsmart.viewmodel.AccountViewModel

class AccountFormsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAccountFormsBinding
    private lateinit var viewModel: AccountViewModel
    private var accountId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountFormsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding.buttonSave.setOnClickListener(this)

        observe()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == binding.buttonSave.id) {
            val validate = validate_forms()

            if (validate) {
                val account = AccountModel().apply {
                    this.id = accountId
                    this.name = binding.editName.text.toString()
                }

                viewModel.save(account)
            }
        }
    }

    private fun validate_forms(): Boolean {
        if (binding.editName.text.isNullOrEmpty()) {
            binding.editName.error = "Nome da Conta está vazia!"

            return false
        }

        return true
    }

    private fun loadData() {
        val bundle = intent.extras

        if (bundle != null) {
            accountId = bundle.getInt(DataBaseConstants.ACCOUNT.ID_NAME)
            viewModel.get(accountId)
        }
    }

    private fun observe() {
        viewModel.account.observe(this, Observer {
            binding.editName.setText(it.name)
        })

        viewModel.saveAccount.observe(this, Observer {
            if (it != "Falha !") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            }
        })
    }
}