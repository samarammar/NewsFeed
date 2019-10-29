package com.samar.newsfeeds.dagger.module


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.newsdata.module.LanguageModule
import com.newsdomain.diinterfaces.AppRemoteUrl
import com.samar.newsfeeds.dagger.module.OkHttpClientModule

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [OkHttpClientModule::class, ApplicationModule::class])
class NetworkModule {


    @Provides
    fun retrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
                 , @AppRemoteUrl baseUrl:String, language: LanguageModule
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
                .setLenient()
        return gsonBuilder.create()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }


}