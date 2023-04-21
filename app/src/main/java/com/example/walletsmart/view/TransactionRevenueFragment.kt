package com.example.walletsmart.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletsmart.constants.DataBaseConstants
import com.example.walletsmart.databinding.FragmentTransactionRevenueBinding
import com.example.walletsmart.view.adapter.TransactionsAdapter
import com.example.walletsmart.view.listener.OnTransactionListener
import com.example.walletsmart.viewmodel.TransactionViewModel

class TransactionRevenueFragment : Fragment() {
    private var _binding: FragmentTransactionRevenueBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TransactionViewModel
    private val adapter = TransactionsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        _binding = FragmentTransactionRevenueBinding.inflate(inflater, container, false)

        binding.recycleTransactionRevenue.layoutManager = LinearLayoutManager(context)
        binding.recycleTransactionRevenue.adapter = adapter

        val listener = object : OnTransactionListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, TransactionFormsActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.TRANSACTION.ID_NAME, id)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getRevenue()
            }

        }

        adapter.attachListener(listener)
        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRevenue()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.transactions.observe(viewLifecycleOwner) {
            adapter.updatedGuests(it)
        }
    }
}