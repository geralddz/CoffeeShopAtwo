package com.app.coffeeshopatwo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.app.coffeeshopatwo.R
import com.app.coffeeshopatwo.database.SharedPref

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPref = SharedPref(this)
        Handler().postDelayed({
            checkAuth()
            finish()
        }, 1000)
    }

    private fun checkAuth() {
        if (sharedPref.isLogIn()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }else if (sharedPref.isLogInAdmin()){
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}