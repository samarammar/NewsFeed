package com.newsdata.datastore

import com.newsdata.datasource.INewsDataStore
import com.newsdata.datasource.INewsRemote
import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS

import io.reactivex.Observable

import javax.inject.Inject

class NewsRemoteDataStore @Inject constructor(private val iNewsRemote: INewsRemote) : INewsDataStore {
    override fun getArticles(): Observable<List<Article>> {
        return iNewsRemote.getArticles()
    }


}