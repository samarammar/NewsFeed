package com.newsdata.repository

import com.newsdata.datastore.NewsFactory
import com.newsdomain.model.Article
import com.newsdomain.repository.INewsRepository
import com.newsdomain.state.BaseVS
import io.reactivex.Observable

import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(val newsFactory: NewsFactory) : INewsRepository {
    override fun getArticles(): Observable<List<Article>> {
        return newsFactory.retrieveDataStore().getArticles()
    }



}