package com.newsdata.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.newsdomain.diinterfaces.AppContext
import com.newsdomain.diinterfaces.AppPreferenceName
import com.newsdomain.diinterfaces.ProjectScope


import javax.inject.Inject

@ProjectScope
open class PreferenceModule @Inject
constructor(@AppContext context: Context, @AppPreferenceName prefName: String, private val gson: Gson) {
    private val preferences: SharedPreferences
    private val CUSTOMERPROFILE = "CUSTOMERPROFILE"

    var language: String?
        get() = this.getString("language", "ar")
        set(currentLanguage) = putString("language", currentLanguage!!)

    init {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }


    fun putString(key: String, value: String?) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean?) {
        val editor = preferences.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

//    fun saveUser(user:User_Entity){
//        putString("user",gson.toJson(user))
//    }
//
//    fun logout(){
//        putString("user",null)
//    }
//    fun getUser():User_Entity?{
//        return gson.fromJson(getString("user",null),User_Entity::class.java)
//    }

    fun getString(key: String, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean?): Boolean? {
        return preferences.getBoolean(key, defaultValue!!)
    }


    fun saveToken(token: String?) {
        putString("token", token)
    }

    fun getToken(): String? {
        return getString("token", null)
    }

    fun removeToken() {
        putString("userData", null)
        putString("token", null)
    }

    fun isCustomer(): Boolean = getBoolean("customer", false)!!
    fun getUserType(): String = if (isCustomer()) "user" else "team"

    fun setAsCustomer() {
        putBoolean("customer", true)
    }

    fun savePlayerId(playerId: String?) {
        putString("PlayerId", playerId)
    }

    fun getPlayerId(): String? {
        return getString("PlayerId", null)
    }

    fun saveUser(user: String) {
        putString("userData", user)
    }

    fun getUser(): String? {
        return getString("userData", null)
    }

}
