package com.app.coffeeshopatwo.ui.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.adapter.AddMenuAdapter
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.database.SharedPref
import com.app.coffeeshopatwo.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity(), AddMenuAdapter.MenuItemClickInterface {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    private lateinit var addMenuAdapter: AddMenuAdapter
    private var listmenu = mutableListOf<com.app.coffeeshopatwo.entity.Menu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbout)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        database = AppDatabase.getInstance(applicationContext)
        sharedPref = SharedPref(this)

        addMenuAdapter = AddMenuAdapter(listmenu,this)
        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = addMenuAdapter
            addMenuAdapter.notifyDataSetChanged()
        }

        binding.btninputmenu.setOnClickListener {
            startActivity(Intent(this, AddMenuActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnLogOut -> {
                val builder = android.app.AlertDialog.Builder(this)
                builder.setTitle("Peringatan !!! ")
                    .setMessage("Apakah Anda Ingin Log Out ? ")
                    .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                        sharedPref.isLogOut()
                        Toast.makeText(this, "Log Out Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }.setNegativeButton("No") { dialog: DialogInterface, which: Int ->
                        dialog.cancel()
                    }.show()
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData(){
        listmenu.clear()
        listmenu.addAll(database.menuDao().getMenu())
        addMenuAdapter.notifyDataSetChanged()
    }

    override fun onDelete(position: Int) {
        database.menuDao().deleteMenu(listmenu[position])
        getData()
    }

    override fun onUpdate(position: Int) {
        val intent = Intent(this, AddMenuActivity::class.java)
        intent.putExtra("id", listmenu[position].menu_id)
        startActivity(intent)
    }
}