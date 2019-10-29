package com.samar.newsfeeds.base

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface ActivityList_View : IBaseMVI {
    fun getIntentList(): Observable<Int>

}