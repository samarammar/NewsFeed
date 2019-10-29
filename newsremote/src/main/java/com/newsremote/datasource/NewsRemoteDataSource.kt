package com.newsremote.datasource

import android.content.Context
import com.newsremote.getErrorMessage
import com.newsremote.service.newsService
import com.newsdata.datasource.INewsRemote
import com.newsdata.module.LanguageModule
import com.newsdata.module.PreferenceModule
import com.newsdomain.diinterfaces.AppContext
import com.newsdomain.model.Article
import io.reactivex.Observable
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
        @AppContext private val context: Context,
        private val service: newsService,
        private val language: LanguageModule,
        private val preferenceModule: PreferenceModule
) : INewsRemote {




    override fun getArticles(): Observable<List<Article>> {
        return service.getArticles("the-next-web","f6e68ec4e2f447be954b038cc6615f7f").map {
            if (it.isSuccessful) {
                it.body()?.articles!!
            } else throw Exception(it.getErrorMessage(context))
        }
    }




}