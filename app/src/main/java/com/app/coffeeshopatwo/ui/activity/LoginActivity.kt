package com.app.coffeeshopatwo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.database.AppDatabase
import com.app.coffeeshopatwo.database.SharedPref
import com.app.coffeeshopatwo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        sharedPref = SharedPref(this)

        binding.btnSignIn.setOnClickListener {
            val username = binding.etUsernameSignIn.text.toString().trim()
            val password = binding.etPasswordSignIn.text.toString().trim()
            val user = database.userDao().loginUser(username,password)
            if (CheckValidation(username,password)) {
                if (username=="admin"&&password=="admin"){
                    sharedPref.setLogInAdmin(true)
                    Toast.makeText(this, "Admin berhasil login", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AdminActivity::class.java))
                }else{
                    let {
                        user.observe(this, Observer {
                            if (it == null) {
                                Toast.makeText(this, "Masukkan Email Dan Password secara benar", Toast.LENGTH_SHORT).show()
                            } else {
                                sharedPref.setLogIn(true)
                                val username = user.value?.username.toString()
                                val uid = user.value?.uid.toString()
                                sharedPref.setUsername(username)
                                sharedPref.setUid(uid)
                                Toast.makeText(this, "Yeah berhasil login", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                        })
                    }
                }
            }

            binding.tvSignUp.setOnClickListener {
                startActivity(Intent(this,RegistrasiActivity::class.java))
            }
        }

    }

    private fun CheckValidation(username: String, pass: String): Boolean {
        if(username.isEmpty()){
            binding.etUsernameSignIn.error = "Masukkan Username Anda"
            binding.etUsernameSignIn.requestFocus()
        } else if (pass.isEmpty()) {
            binding.etPasswordSignIn.error = "Masukkan Password Anda"
            binding.etPasswordSignIn.requestFocus()
        } else {
            binding.etPasswordSignIn.error = null
            return true
        }
        Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
        return false
    }
}