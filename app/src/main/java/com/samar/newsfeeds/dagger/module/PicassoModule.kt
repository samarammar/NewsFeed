package com.samar.newsfeeds.dagger.module

import android.content.Context
import com.newsdomain.diinterfaces.AppContext
import com.squareup.picasso.OkHttp3Downloader
import okhttp3.OkHttpClient
import dagger.Provides
import com.squareup.picasso.Picasso
import dagger.Module


@Module
class PicassoModule {

    @Provides
    fun picasso(@AppContext context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context).downloader(okHttp3Downloader).build()
    }

    @Provides
    fun okHttp3Downloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }




}