package com.newsdomain.repository


import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS
import io.reactivex.Observable

interface INewsRepository {

    fun getArticles(): Observable<List<Article>>

}