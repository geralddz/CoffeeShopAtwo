package com.app.coffeeshopatwo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.adapter.HistoryAdapter
import com.app.coffeeshopatwo.adapter.MenuAdapter
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.database.SharedPref
import com.app.coffeeshopatwo.databinding.FragmentHistoryBinding
import com.app.coffeeshopatwo.databinding.FragmentMenuBinding
import com.app.coffeeshopatwo.entity.Menu
import com.app.coffeeshopatwo.entity.Transaction

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    private lateinit var historyAdapter: HistoryAdapter
    private var uid = 0
    private var listhistory = mutableListOf<Transaction>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext())
        sharedPref = SharedPref(requireContext())
        uid = sharedPref.getUid()!!.toInt()

        historyAdapter = HistoryAdapter(listhistory)
        binding.rvHistory.apply {
            getData()
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = historyAdapter
            historyAdapter.notifyDataSetChanged()
        }
    }

    fun getData(){
        listhistory.clear()
        listhistory.addAll(database.transactionDao().getTransaction(uid))
        historyAdapter.notifyDataSetChanged()
    }

}