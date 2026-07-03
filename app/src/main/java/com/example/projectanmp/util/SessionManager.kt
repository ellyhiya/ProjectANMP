package com.example.projectanmp.util

import android.content.Context

class SessionManager(context: Context) {

    private val pref =
        context.getSharedPreferences("LOGIN_SESSION", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOGIN = "is_login"
    }

    fun setLogin(isLogin: Boolean) {
        pref.edit().putBoolean(KEY_LOGIN, isLogin).apply()
    }

    fun isLogin(): Boolean {
        return pref.getBoolean(KEY_LOGIN, false)
    }

    fun logout() {
        pref.edit().clear().apply()
    }
}