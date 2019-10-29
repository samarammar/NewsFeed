package com.newsdata.datastore

import com.newsdata.datasource.INewsCach
import com.newsdata.datasource.INewsDataStore
import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS
import io.reactivex.Observable
import javax.inject.Inject

class NewsCacheDataStore @Inject constructor(private val iNewsCache: INewsCach) : INewsDataStore {
    override fun getArticles(): Observable<List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}