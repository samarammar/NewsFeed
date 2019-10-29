package com.trippl3dev.listlibrary

import android.content.Context
import android.content.SharedPreferences

internal class PreferenceModule(private val context: Context) {
    companion object {
        fun getInstance(context: Context):PreferenceModule{
            return PreferenceModule(context)
        }
    }

    private val preferences: SharedPreferences = context.getSharedPreferences("list", Context.MODE_PRIVATE)

    var loadingId: Int?
        get() = this.getInt("loadingId", -1)
        set(value) = putInt("loadingId", value!!)

    var errorId: Int?
        get() = this.getInt("errorId", -1)
        set(value) = putInt("errorId", value!!)
    var emptyId: Int?
        get() = this.getInt("emptyId", -1)
        set(value) = putInt("emptyId", value!!)


    private fun putInt(key: String, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int? {
        return preferences.getInt(key, defaultValue)
    }
}