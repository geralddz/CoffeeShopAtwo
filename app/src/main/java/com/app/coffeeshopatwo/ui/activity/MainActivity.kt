package com.app.coffeeshopatwo.ui.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.adapter.MenuAdapter
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.database.SharedPref
import com.app.coffeeshopatwo.databinding.ActivityMainBinding
import com.app.coffeeshopatwo.ui.fragment.CartFragment
import com.app.coffeeshopatwo.ui.fragment.HistoryFragment
import com.app.coffeeshopatwo.ui.fragment.MenuFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbout)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        database = AppDatabase.getInstance(applicationContext)
        sharedPref = SharedPref(this)
        binding.tvUser.text = sharedPref.getUsername().toString()

        replacefragment(MenuFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> replacefragment(MenuFragment())
                R.id.cart -> replacefragment(CartFragment())
                R.id.history -> replacefragment(HistoryFragment())
            }
            true
        }

    }


    private fun replacefragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
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
}