package com.samar.newsfeeds.base.activity

import android.content.Context
import com.newsdata.module.LanguageModule
import com.samar.newsfeeds.application.MyApplication


import javax.inject.Inject

open class InjectorAll{
    @Inject
    open lateinit var languageModule: LanguageModule

    fun attach(context: Context){
        (context.applicationContext as MyApplication).applicationComponent.inject(this)
    }
}
