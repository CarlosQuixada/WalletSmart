package com.example.walletsmart.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletsmart.databinding.FragmentResumeBinding
import com.example.walletsmart.viewmodel.TransactionViewModel

class ResumeFragment : Fragment() {
    private var _binding: FragmentResumeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TransactionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        _binding = FragmentResumeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initializer_view()
    }

    fun initializer_view() {
        val balance = String.format("%.2f", viewModel.calculateBalance())
        binding.textBalanceValue.text = "R$ $balance"
    }
}