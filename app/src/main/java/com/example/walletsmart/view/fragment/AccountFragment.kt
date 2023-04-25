package com.example.walletsmart.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletsmart.constants.DataBaseConstants
import com.example.walletsmart.databinding.FragmentAccountBinding
import com.example.walletsmart.view.activity.AccountFormsActivity
import com.example.walletsmart.view.activity.TransactionFormsActivity
import com.example.walletsmart.view.adapter.AccountsAdapter
import com.example.walletsmart.view.listener.OnAccountListener
import com.example.walletsmart.viewmodel.AccountViewModel

class AccountFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel
    private val adapter = AccountsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        binding.recycleAllAccount.layoutManager = LinearLayoutManager(context)
        binding.recycleAllAccount.adapter = adapter

        binding.buttonCreateAccount.setOnClickListener(this)

        val listener = object : OnAccountListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, AccountFormsActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.ACCOUNT.ID_NAME, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }

        }

        adapter.attachListener(listener)
        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v.id == binding.buttonCreateAccount.id) {
            startActivity(Intent(context, AccountFormsActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.accounts.observe(viewLifecycleOwner) {
            adapter.updatedGuests(it)
        }
    }
}