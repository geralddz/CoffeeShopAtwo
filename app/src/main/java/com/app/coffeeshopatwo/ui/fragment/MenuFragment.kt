package com.app.coffeeshopatwo.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.adapter.MenuAdapter
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.databinding.FragmentMenuBinding
import com.app.coffeeshopatwo.entity.Menu
import com.app.coffeeshopatwo.ui.activity.LoginActivity
import java.util.Locale

class MenuFragment : Fragment(), MenuAdapter.MenuItemClickInterface {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var database: AppDatabase
    private lateinit var menuAdapter: MenuAdapter
    private var listmenu = mutableListOf<Menu>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext())

        menuAdapter = MenuAdapter(listmenu,this)
        binding.rvMenu.apply {
            getData()
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            adapter = menuAdapter
            menuAdapter.notifyDataSetChanged()
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newtext: String?): Boolean {
                if (newtext != null) {
                    filterlist(newtext)
                }
                return true
            }

        })

    }

    private fun filterlist(query : String){
        if (query!=null){
            val filterlist = ArrayList<Menu>()
            for (i in listmenu){
                if (i.nama?.toLowerCase(Locale.ROOT)?.contains(query) == true){
                    filterlist.add(i)
                }
            }

            if (filterlist.isEmpty()){
                Toast.makeText(requireContext(), "Menu Tidak Ada", Toast.LENGTH_SHORT).show()
            }else{
                menuAdapter.setFilterList(filterlist)
            }
        }
    }
    private fun getData(){
        listmenu.clear()
        listmenu.addAll(database.menuDao().getMenu())
        menuAdapter.notifyDataSetChanged()
    }

    override fun onItemClicked(position: Int) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Order")
            .setMessage("Continue to order ? ")
            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                val bundle = Bundle()
                listmenu[position].menu_id?.let { bundle.putInt("mid", it) }
                val fragmentadd = CartFragment()
                fragmentadd.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragmentadd)?.commit()
            }.setNegativeButton("No") { dialog: DialogInterface, which: Int ->
                dialog.cancel()
            }.show()
    }


}