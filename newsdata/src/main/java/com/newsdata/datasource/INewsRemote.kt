package com.newsdata.datasource



import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS
import io.reactivex.Observable

interface INewsRemote {
    fun getArticles(): Observable<List<Article>>


}