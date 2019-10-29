package com.newsdata.datasource



import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS
import io.reactivex.Observable

interface INewsDataStore {
    //    Have all requests Observable interface and implemented in data cach and remote

    fun getArticles(): Observable<List<Article>>




}