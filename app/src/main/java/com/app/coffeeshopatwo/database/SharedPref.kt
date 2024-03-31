package com.app.coffeeshopatwo.database

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    val PRIVATE_MODE = 0
    private val PREF_NAME = "SharedPreferences"
    private val IS_LOGIN = "is_login"
    private val IS_LOGIN_ADMIN = "is_login_admin"

    var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref.edit()

    fun setUsername(name: String) {
        editor?.putString("name", name)
        editor?.commit()
    }

    fun setUid(uid: String) {
        editor?.putString("uid", uid)
        editor?.commit()
    }

    fun getUid(): String? {
        return pref.getString("uid", "")
    }

    fun getUsername(): String? {
        return pref.getString("name", "")
    }


    fun setLogIn(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun setLogInAdmin(isLoginadmin: Boolean) {
        editor?.putBoolean(IS_LOGIN_ADMIN, isLoginadmin)
        editor?.commit()
    }

    fun isLogIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }

    fun isLogInAdmin(): Boolean {
        return pref.getBoolean(IS_LOGIN_ADMIN, false)
    }

    fun isLogOut() {
        editor?.clear()
        editor?.commit()
    }
}