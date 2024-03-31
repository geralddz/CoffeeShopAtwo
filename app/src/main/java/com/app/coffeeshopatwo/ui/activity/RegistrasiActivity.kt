package com.app.coffeeshopatwo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.databinding.ActivityRegistrasiBinding
import com.app.coffeeshopatwo.entity.User

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrasiBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        binding.btnSignUp.setOnClickListener {
            val username = binding.etUsernameSignUp.text.toString().trim()
            val pass = binding.etPasswordSignUp.text.toString().trim()

            if (CheckValidation(username,pass)){
                database.userDao().register(User(null, username, pass))
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun CheckValidation(nama: String, pass: String): Boolean {
        if(nama.isEmpty()){
            binding.etUsernameSignUp.error = "Masukkan Username Anda"
            binding.etPasswordSignUp.requestFocus()
        } else if (pass.isEmpty()) {
            binding.etPasswordSignUp.error = "Masukkan Password Anda"
            binding.etPasswordSignUp.requestFocus()
        } else {
            binding.etPasswordSignUp.error = null
            return true
        }
        Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
        return false
    }
}