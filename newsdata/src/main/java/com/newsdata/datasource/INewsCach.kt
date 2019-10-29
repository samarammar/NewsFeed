package com.newsdata.datasource


import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS
import io.reactivex.Observable

interface INewsCach {
    //    All things you need to cach userdata,language ,login state
    fun getArticles(): Observable<List<Article>>



}