package com.app.coffeeshopatwo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.databinding.ItemHistoryBinding
import com.app.coffeeshopatwo.entity.Transaction
import com.app.coffeeshopatwo.utils.BitmapConverter
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private var listhistory: List<Transaction>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.HistoryViewHolder {
        val view = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val time = listhistory[position].date
        val photo = listhistory[position].foto.toString()
        val total = listhistory[position].total
        val qty = listhistory[position].qty.toString()
        if (photo=="null"){
            holder.binding.ivpotohistory.setImageResource(R.drawable.photo)
        }else{
            val bitmap = BitmapConverter.converterStringToBitmap(listhistory[position].foto.toString())
            holder.binding.ivpotohistory.setImageBitmap(bitmap)
        }
        holder.binding.tvnama.text = listhistory[position].nama
        holder.binding.tvqty.text = "Qty : $qty"
        holder.binding.tvdate.text = time
        holder.binding.tvtotal.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(total)
    }

    override fun getItemCount(): Int {
        return listhistory.size
    }

}