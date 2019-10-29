package com.samar.newsfeeds.base.activity

import android.os.Bundle

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.newsdomain.state.BaseVS
import com.samar.newsfeeds.base.MVIBasePresenter


abstract class BaseActivity : BaseMVIActivity<MvpView, MVIBasePresenter<MvpView, *>>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolBar()
    }

    private fun setupToolBar() {

    }

    override fun createPresenter(): MVIBasePresenter<MvpView, *> {
        return BasePresenter()
    }


}

class BasePresenter:MVIBasePresenter<MvpView, BaseVS>(){
    override fun bindIntents() {
    }
}