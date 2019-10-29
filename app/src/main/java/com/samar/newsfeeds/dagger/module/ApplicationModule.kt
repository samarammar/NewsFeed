package com.samar.newsfeeds.dagger.module

import android.app.Application
import android.content.Context
import com.newsdomain.diinterfaces.AppContext
import com.newsdomain.diinterfaces.AppPreferenceName
import com.newsdomain.diinterfaces.AppRemoteUrl
import com.newsdomain.diinterfaces.ProjectScope



import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val DOMAIN = "https://newsapi.org"

@Module
class ApplicationModule {
    @ProjectScope
    @AppRemoteUrl
    @Provides
    fun serviceURl(): String {
        return "$DOMAIN/v1/"
    }


    @AppPreferenceName
    @Provides
    fun setPreferenceName(): String {
        return "NewsFeeds"
    }


    @AppContext
    @Provides
    fun context(application: Application): Context {
        return application.applicationContext
    }

}