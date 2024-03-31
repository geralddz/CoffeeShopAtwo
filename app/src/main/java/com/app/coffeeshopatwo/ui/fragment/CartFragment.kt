package com.app.coffeeshopatwo.ui.fragment

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.adapter.MenuAdapter
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.database.SharedPref
import com.app.coffeeshopatwo.databinding.FragmentCartBinding
import com.app.coffeeshopatwo.databinding.FragmentMenuBinding
import com.app.coffeeshopatwo.entity.Transaction
import com.app.coffeeshopatwo.utils.BitmapConverter
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    private lateinit var img:String
    private lateinit var namakopi:String
    private var mid = 0
    private var uid = 0
    private var jumlah = 1
    private var totalharga = 0
    private var total = 0
    private var tax = 0
    private var harga = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext())
        sharedPref = SharedPref(requireContext())
        uid = sharedPref.getUid()!!.toInt()
        getData()

        binding.btnTambah.setOnClickListener{
            jumlah++
            binding.tvjumlah.text = jumlah.toString()
            totalharga = harga*jumlah
            binding.nprice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(totalharga)
            tax = (totalharga*0.1).toInt()
            total = (totalharga+tax)
            binding.taxprice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tax)
            binding.total.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(total)
        }

        binding.btnKurang.setOnClickListener {
            if(jumlah>=2){
                jumlah--
                binding.tvjumlah.text = jumlah.toString()
                totalharga = harga*jumlah
                binding.nprice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(totalharga)
                tax = (totalharga*0.1).toInt()
                total = (totalharga+tax)
                binding.taxprice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tax)
                binding.total.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(total)
            }
        }
        binding.btnOrder.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Confirmation Order")
                .setMessage("Continue to Payment ? ")
                .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                    val currentTimeMillis = System.currentTimeMillis()
                    val outputFormat = "MMM dd, yyyy h:mm a"
                    val formattedDate = convertMillisToString(currentTimeMillis, outputFormat)
                    database.transactionDao().insertTransaction(Transaction
                        (null,uid.toString(),formattedDate,namakopi,jumlah,total,"Succeed",img))
                    Toast.makeText(requireContext(), "Order Succeed", Toast.LENGTH_SHORT).show()
                    fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, HistoryFragment())?.commit()
                }.setNegativeButton("No") { dialog: DialogInterface, which: Int ->
                    dialog.cancel()
                }.show()
        }


    }

    fun getData(){
        val pesan = arguments?.getInt("mid")
        if (pesan!=null){
            val menu = database.menuDao().getMenuId(pesan)
            mid = menu.menu_id!!.toInt()
            harga = menu.harga!!
            namakopi = menu.nama.toString()
            binding.tvkopi1.text = menu.nama.toString()
            binding.tvkopi.text = menu.nama.toString()
            binding.tvdsckopi.text = menu.deskripsi.toString()
            binding.tvhargakopi.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(harga)
            binding.nprice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(harga)
            tax = (harga * 0.1).toInt()
            binding.taxprice.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tax)
            total = harga + tax
            binding.total.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(total)
            img = menu.foto.toString()
            if (img=="null"){
                binding.ivpotokopi.setImageResource(R.drawable.photo)
                binding.ivpotokopi1.setImageResource(R.drawable.photo)
            }else{
                val bitmap = BitmapConverter.converterStringToBitmap(img)
                binding.ivpotokopi.setImageBitmap(bitmap)
                binding.ivpotokopi1.setImageBitmap(bitmap)
            }
        }
    }

    fun convertMillisToString(millis: Long, outputFormat: String): String {
        val dateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        val date = Date(millis)
        return dateFormat.format(date)
    }

}