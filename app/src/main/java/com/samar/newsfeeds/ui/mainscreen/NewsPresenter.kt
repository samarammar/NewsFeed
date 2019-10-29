package com.samar.newsfeeds.ui.mainscreen

import com.newsdomain.interactor.usecase.ArticlesUsecase
import com.newsdomain.state.BaseVS
import com.samar.newsfeeds.base.IBaseMVI
import com.samar.newsfeeds.base.MVIBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsPresenter  @Inject constructor(private val usecase: ArticlesUsecase): MVIBasePresenter<IBaseMVI, BaseVS>() {

    override fun bindIntents() {
        val observable = usecase.getArticles()
                .startWith(BaseVS.Loading.getWithType(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(),true)

        subscribeViewState(observable, IBaseMVI::render)
    }
}