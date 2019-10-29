package com.samar.newsfeeds.base.fragment

//import com.common.ui.base.fragment.BaseFragment
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.samar.newsfeeds.base.activity.BaseMVIActivity

open class SBaseFragment : BaseFragment<MvpView, BaseMVIActivity.BasePresenter>() {

    override fun createPresenter(): BaseMVIActivity.BasePresenter {
        return BaseMVIActivity.BasePresenter()
    }
}