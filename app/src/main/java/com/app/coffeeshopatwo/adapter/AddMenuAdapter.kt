package com.app.coffeeshopatwo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.entity.Menu
import com.app.coffeeshopatwo.ui.activity.AdminActivity
import com.app.coffeeshopatwo.utils.BitmapConverter
import java.text.NumberFormat
import java.util.Locale

class AddMenuAdapter(private var listmenu: List<Menu>, private val menuItemClickInterface: AdminActivity) : RecyclerView.Adapter<AddMenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nama = view.findViewById<TextView>(R.id.tvnama_menu)
        val desc = view.findViewById<TextView>(R.id.tvdesc_menu)
        val harga = view.findViewById<TextView>(R.id.tvharga_menu)
        val photo = view.findViewById<ImageView>(R.id.ivpoto_menu)
        val edit = view.findViewById<ImageView>(R.id.ivedit)
        val hapus = view.findViewById<ImageView>(R.id.ivdelete)
    }

    interface MenuItemClickInterface {
        fun onDelete(position: Int)
        fun onUpdate(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.nama.text = listmenu[position].nama
        holder.desc.text = listmenu[position].deskripsi
        holder.harga.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(listmenu[position].harga)
        val photo = listmenu[position].foto.toString()
        if (photo=="null"){
            holder.photo.setImageResource(R.drawable.photo)
        }else{
            val bitmap = BitmapConverter.converterStringToBitmap(listmenu[position].foto.toString())
            holder.photo.setImageBitmap(bitmap)
        }

        holder.hapus.setOnClickListener {
            menuItemClickInterface.onDelete(position)
        }

        holder.edit.setOnClickListener {
            menuItemClickInterface.onUpdate(position)
        }
    }

    override fun getItemCount(): Int {
        return listmenu.size
    }

}