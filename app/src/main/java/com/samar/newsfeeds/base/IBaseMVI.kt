package com.samar.newsfeeds.base

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.newsdomain.state.BaseVS
import io.reactivex.Observable

interface IBaseMVI : MvpView{
    fun render(baseVS: BaseVS)
    fun  retryIntent():Observable<Boolean>{return Observable.just(true)}
    fun  retryIntentPass():Observable<Boolean>{return Observable.just(true)}

}