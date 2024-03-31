package com.app.coffeeshopatwo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.entity.Menu
import com.app.coffeeshopatwo.databinding.ItemMenuBinding
import com.app.coffeeshopatwo.ui.fragment.MenuFragment
import com.app.coffeeshopatwo.utils.BitmapConverter
import java.text.NumberFormat
import java.util.Locale

class MenuAdapter(private var listmenu: List<Menu>, private val menuItemClickInterface: MenuFragment) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

    interface MenuItemClickInterface {
        fun onItemClicked(position: Int)
    }

    fun setFilterList(listmenu : List<Menu>){
        this.listmenu = listmenu
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val view = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        holder.binding.tvnamaMenu.text = listmenu[position].nama.toString()
        holder.binding.tvhargaMenu.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(listmenu[position].harga)
        val photo = listmenu[position].foto.toString()
        if (photo=="null"){
            holder.binding.ivpotoMenu.setImageResource(R.drawable.photo)
        }else{
            val bitmap = BitmapConverter.converterStringToBitmap(listmenu[position].foto.toString())
            holder.binding.ivpotoMenu.setImageBitmap(bitmap)
        }

        holder.binding.layoutMenu.setOnClickListener {
            menuItemClickInterface.onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return listmenu.size
    }
}